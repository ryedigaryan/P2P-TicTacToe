package generic.networking.test;

import generic.networking.client.Client;
import generic.networking.server.Server;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class ClientServerTest {

    private static final String CLIENT_AGREEMENT_OBJECT = "CLIENT_WANTS_TO_CONNECT";
    public static final InetAddress SERVER_MULTICAST_GROUP;
    public static final int SERVER_MULTICAST_PORT = 4446;
    public static final int SERVER_PORT = 5050;
    public static final byte[] SERVER_MULTICAST_MESSAGE = "SERVER_SEARCHES_FOR_CLIENT".getBytes();

    static {
        InetAddress g;
        try {
            g = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            g = null;
        }
        SERVER_MULTICAST_GROUP = g;
    }

    public static class ServerRunner {
        public static void main(String[] args) throws Exception {
            Server server = new Server();
            server.setServerPort(SERVER_PORT);
            server.setMaxClientsCount(3);
            server.setClientConnectionAgreementObject(CLIENT_AGREEMENT_OBJECT);
            server.setMulticastGroup(SERVER_MULTICAST_GROUP);
            server.setMulticastPort(SERVER_MULTICAST_PORT);
            server.setMulticastNotificationDelaySeconds(2);
            server.setMulticastMessage(SERVER_MULTICAST_MESSAGE);
            server.run();
        }
    }

    public static class ClientRunner {
        public static void main(String[] args) throws Exception {
            byte[] nameNoise = new byte[8];
            new Random().nextBytes(nameNoise);
            String name = "CLIENT_" + new String(nameNoise);
            Client client = new Client();
            client.setClientConnectionAgreementObject(CLIENT_AGREEMENT_OBJECT);
            client.setName(name);
            client.setServerMulticastGroup(SERVER_MULTICAST_GROUP);
            client.setServerMulticastMessage(SERVER_MULTICAST_MESSAGE);
            client.setServerMulticastPort(SERVER_MULTICAST_PORT);
            client.run();
        }
    }
}
