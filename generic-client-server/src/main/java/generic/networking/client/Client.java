package generic.networking.client;

import generic.networking.common.ConnectionListener;
import generic.networking.common.MulticastGroupListener;
import generic.networking.common.MulticastGroupNotifier;
import generic.networking.grdon.TemporarySolution;
import generic.networking.test.ClientServerTest;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Getter
@Setter
public class Client implements Runnable {
    @Setter(AccessLevel.PRIVATE)
    private MulticastSocket multicastSocket;
    private Object clientConnectionAgreementObject;
    private byte[] serverMulticastMessage;
    private InetAddress serverMulticastGroup;
    private int serverMulticastPort;


    @Override
    public void run() {
        System.out.println("Running client name: " + getName());
        TemporarySolution.rethrowAsError(
                () -> {
                    setMulticastSocket(new MulticastSocket(getServerMulticastPort()));
                    runServerGroupListener(
                            (serverPacket) -> TemporarySolution.rethrowAsError(
                                    () -> this.addToAddressSet(serverPacket)
                            )
                    );
                }
        );
    }

    private void runServerGroupListener(Consumer<DatagramPacket> serverPacketConsumer) throws IOException {
        MulticastGroupListener listener = new MulticastGroupListener(
                getMulticastSocket(),
                () -> new byte[getServerMulticastMessage().length],
                getServerMulticastGroup(),
                getServerMulticastPort()
        );

        listener.setMulticastPacketHandler(serverPacketConsumer::accept);
        new Thread(() -> {
            while (true) {
                listener.run();
            }
        }).start();
    }

    @Setter(AccessLevel.PRIVATE)
    private Set<InetAddress> availableServerAddresses = ConcurrentHashMap.newKeySet();

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    TemporarySolution.InetAddressSetFrame frame = new TemporarySolution.InetAddressSetFrame();

    private Pair<InetAddress, Integer> getLocalNetworkAddress(SocketAddress remoteAddress) throws SocketException {
        DatagramSocket sock = new DatagramSocket();
        // connect is needed to bind the socket and retrieve the local address
        // later (it would return 0.0.0.0 otherwise)
        sock.connect(remoteAddress);

        final InetAddress localAddress = sock.getLocalAddress();
        int port = sock.getPort();
        sock.disconnect();
        sock.close();
        sock = null;

        return new Pair<>(localAddress, port);
    }

    private boolean alreadyConnected;
    private void addToAddressSet(DatagramPacket serverPacket) throws IOException {

        if (!alreadyConnected && Arrays.equals(serverPacket.getData(), getServerMulticastMessage())) {
            Pair<InetAddress, Integer> localAddress = getLocalNetworkAddress(serverPacket.getSocketAddress());
            getAvailableServerAddresses().add(localAddress.getKey());
            frame.getListModel().add(localAddress.getKey());
            connectToServer(localAddress.getKey().getHostAddress(), localAddress.getValue());
            alreadyConnected = true;
        } else {
            System.out.println("Found wrong datagram packet: " + new String(serverPacket.getData()));
        }
    }

    private String name;

    private void connectToServer(String address, int port) throws IOException {
        Socket socket = new Socket("192.168.43.96", ClientServerTest.SERVER_PORT);
        ObjectOutputStream streamToServer = new ObjectOutputStream(socket.getOutputStream());
        streamToServer.writeObject(getClientConnectionAgreementObject());
        streamToServer.writeObject(name);
        streamToServer.flush();
        System.out.println("Ok, imagine I connected to Server " + address + ":" + port);
    }
}
