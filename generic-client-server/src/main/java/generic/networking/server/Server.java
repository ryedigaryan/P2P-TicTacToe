package generic.networking.server;

import generic.networking.common.MulticastGroupNotifier;
import generic.networking.grdon.TemporarySolution;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Server implements Runnable {

    @Setter(AccessLevel.PRIVATE)
    private ServerSocket serverSocket;
    @Setter(AccessLevel.PRIVATE)
    private MulticastSocket multicastSocket;
    @Setter(AccessLevel.PRIVATE)
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    @Setter(AccessLevel.PRIVATE)
    private ScheduledExecutorService multicastThreadPool;
    @Setter(AccessLevel.PRIVATE)
    private ConcurrentLinkedQueue<Socket> connectedClients = new ConcurrentLinkedQueue<>();
    @Setter(AccessLevel.PRIVATE)
    private Boolean shouldNotifyGroup;

    private int multicastPort;
    private int maxClientsCount;
    private InetAddress multicastGroup;
    private byte[] multicastMessage;
    private int multicastNotificationDelaySeconds;
    private Object clientConnectionAgreementObject;
    private final Object connectionAcceptanceLock = new Object();

    @Override
    public void run() {
        multicastThreadPool = Executors.newScheduledThreadPool(maxClientsCount);
        assert maxClientsCount > 0 : "Maximum number of connected clients should be grater than 0";
        TemporarySolution.rethrowAsError(() -> {
            setMulticastSocket(new MulticastSocket());
            runServer();
            getCachedThreadPool().submit(this::runClientAcceptor);
            runServerGroupNotifier();
        });
    }

    private int serverPort;

    private void runServer() throws IOException {
        setServerSocket(new ServerSocket(serverPort));
    }

    private void runClientAcceptor() {
        TemporarySolution.rethrowAsError(() -> {
            while (canAcceptClientConnection()) {
                Socket clientSocket = getServerSocket().accept();
                if (canAcceptClientConnection()) {
                    getCachedThreadPool().submit(new ClientAgreementChecker(clientSocket));
                }
            }
        });
    }


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
        boolean succeeded = getConnectedClients().add(clientSocket);
        System.out.println("Successfully connected client: " + clientSocket.getInetAddress());
        assert succeeded : "Could not add client into clientsList";
        new Thread(
                () -> TemporarySolution.rethrowAsError(
                        () -> readAndPrintObj(clientSocket)
                )
        )
                .start();
    }

    private void readAndPrintObj(Socket socket) throws IOException, ClassNotFoundException {
        Object o = getClientInputStream(socket.getInputStream()).readObject();
        System.out.println(socket.getInetAddress() + " name is " + o);
    }

    private void runServerGroupNotifier() {
        MulticastGroupNotifier multicastNotifier = new MulticastGroupNotifier(
                getMulticastSocket(),
                this::getMulticastMessage,
                getMulticastGroup(),
                getMulticastPort()
        );

        getMulticastThreadPool().scheduleWithFixedDelay(
                multicastNotifier,
                0,
                getMulticastNotificationDelaySeconds(),
                TimeUnit.SECONDS
        );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Classes
    ///////////////////////////////////////////////////////////////////////////

    @Getter(AccessLevel.NONE)
    private ObjectInputStream clientInputStream;
    private synchronized ObjectInputStream getClientInputStream(InputStream socketIS) {
        if(clientInputStream == null) {
            try {
                clientInputStream = new ObjectInputStream(socketIS);
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return clientInputStream;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private class ClientAgreementChecker implements Runnable {

        private final Socket clientSocket;

        @Override
        public void run() {
            if (Thread.currentThread().isInterrupted())
                return;

            // check the client's response to understand weather client wants to connect to our game server,
            // or it is some unknown client to which Server does not want to connect
            final Object actualConnectionAgreementObject;
            try {
                actualConnectionAgreementObject = getClientInputStream(getClientSocket().getInputStream()).readObject();
                if (actualConnectionAgreementObject.equals(getClientConnectionAgreementObject())) {
                    if (Thread.currentThread().isInterrupted())
                        return;
                    handleClientConnection(getClientSocket());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Server: unable to read agreement object from " + getClientSocket().getInetAddress());
            }
        }
    }

}
