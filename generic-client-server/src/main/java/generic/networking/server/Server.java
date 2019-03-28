package generic.networking.server;

import generic.networking.grdon.TemporarySolution;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
@RequiredArgsConstructor
public class Server implements Runnable {

    @Setter(AccessLevel.PRIVATE)
    private ServerSocket serverSocket;

    @Setter(AccessLevel.NONE)
    private ConcurrentLinkedQueue<Socket> connectedClients;

    private int maxClientsCount;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    @NonNull
    private byte[] clientMulticastMessage;
    @NonNull
    private Object clientConnectionAgreementObject;

    @NonNull
    private byte[] serverMulticastMessage;

    @NonNull
    private InetAddress multicastGroup;
    private int multicastPort;

    @Override
    public void run() {
        assert maxClientsCount > 0 : "Maximum number of connected clients should be grater than 0";
        TemporarySolution.rethrowAsError(() -> {
            runServer();
            runClientAcceptor();
            runGroupListener();
            runGroupNotifier();
        });
    }

    private void runServer() throws IOException {
        setServerSocket(new ServerSocket());
    }

    private void runClientAcceptor() throws IOException {
        while (canAcceptClientConnection()) {
            Socket clientSocket = getServerSocket().accept();
            if (canAcceptClientConnection()) {
                getThreadPool().submit(new ClientAgreementChecker(clientSocket));
            }
        }
    }

    private final Object connectionAcceptanceLock = new Object();

    // called by ClientAgreementChecker when it ensures that the client is correct clients
    // and it wants to connect to the server
    private void handleClientConnection(Socket clientSocket) {
        synchronized (getConnectionAcceptanceLock()) {
            if (canAcceptClientConnection()) {
                acceptClientConnection(clientSocket);
            }
        }
    }

    private boolean canAcceptClientConnection() {
        return getConnectedClients().size() < getMaxClientsCount();
    }

    private void acceptClientConnection(Socket clientSocket) {
        getConnectedClients().add(clientSocket);
    }

    private void runGroupListener() {
        getThreadPool().submit(new GroupListener());
    }

    private void handleClientMulticastMessage(DatagramPacket multicastPacket) {
        synchronized (getShouldNotifyGroup()) {
            // if we should notify group then no need to compare packets
            if (!getShouldNotifyGroup() && Arrays.equals(getClientMulticastMessage(), multicastPacket.getData())) {
                setShouldNotifyGroup(Boolean.TRUE);
            }
        }
    }

    private void runGroupNotifier() {
        getThreadPool().submit(new GroupNotifier());
    }

    private void postNotification() {
        synchronized (getShouldNotifyGroup()) {
            setShouldNotifyGroup(Boolean.FALSE);
        }
    }

    private Boolean shouldNotifyGroup;

    private byte[] createClientMulticastBuffer() {
        return new byte[getClientMulticastMessage().length];
    }

    private byte[] createServerMulticastBuffer() {
        return new byte[getServerMulticastMessage().length];
    }

    ///////////////////////////////////////////////////////////////////////////
    // Classes
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    @Setter
    @RequiredArgsConstructor
    private class ClientAgreementChecker implements Runnable {

        private final Socket clientSocket;

        @Override
        public void run() {
            if (Thread.currentThread().isInterrupted())
                return;
            // obtain client input stream
            final ObjectInputStream clientInputStream;
            try {
                clientInputStream = new ObjectInputStream(getClientSocket().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server: unable to get client input stream from " + getClientSocket().getInetAddress());
                // terminate execution if unable to obtain client input stream
                return;
            }

            if (Thread.currentThread().isInterrupted())
                return;

            // check the client's response to understand weather client wants to connect to our game server,
            // or it is some unknown client to which Server does not want to connect
            final Object actualConnectionAgreementObject;
            try {
                actualConnectionAgreementObject = clientInputStream.readObject();
                if (actualConnectionAgreementObject.equals(getClientConnectionAgreementObject())) {
                    handleClientConnection(getClientSocket());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Server: unable to read agreement object from " + getClientSocket().getInetAddress());
            }
        }
    }

    @RequiredArgsConstructor
    private class GroupListener implements Runnable {

        @Override
        public void run() {
            MulticastSocket socket;
            try {
                socket = new MulticastSocket(getMulticastPort());
                socket.joinGroup(getMulticastGroup());
            } catch (Exception e) {
                throw new Error(e);
            }

            while (!Thread.currentThread().isInterrupted()) {
                TemporarySolution.rethrowAsError(() -> {
                    byte[] clientMulticastMessage = createClientMulticastBuffer();
                    DatagramPacket actualClientPacket = new DatagramPacket(clientMulticastMessage, clientMulticastMessage.length);
                    socket.receive(actualClientPacket);
                    handleClientMulticastMessage(actualClientPacket);
                });
            }

            try {
                socket.leaveGroup(getMulticastGroup());
                socket.close();
            } catch (IOException e) {
                throw new Error(e);
            }
        }
    }

    private class GroupNotifier implements Runnable {

        @Override
        public void run() {
            DatagramSocket socket;
            try {
                socket = new DatagramSocket();
            } catch (Exception e) {
                throw new Error(e);
            }

            byte[] buf = createServerMulticastBuffer();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, getMulticastGroup(), getMulticastPort());
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (getShouldNotifyGroup()) {
                        socket.send(packet);
                        postNotification();
                    } else {
                        Thread.sleep(TemporarySolution.AFTER_NOTIFICATION_SLEEP_MS);
                    }
                } catch (IOException e) {
                    throw new Error(e);
                } catch (InterruptedException e) {
                    break;
                }
            }

            socket.close();
        }
    }
}
