package generic.networking.test;

import generic.networking.common.MulticastConfig;
import generic.networking.endpoint.Client;
import generic.networking.endpoint.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientServerTest {

    private static final String CLIENT_AGREEMENT_OBJECT = "CLIENT_WANTS_TO_CONNECT";
    public static final InetSocketAddress SERVER_MULTICAST_GROUP = new InetSocketAddress("230.0.0.0", 5959);
    public static final int SERVER_MULTICAST_PORT = 4446;
    public static final int SERVER_PORT = 5050;
    public static final String SERVER_MULTICAST_MESSAGE = "SERVER_SEARCHES_FOR_CLIENT";

    private static ScheduledExecutorService shtp = Executors.newScheduledThreadPool(3);
    private static ExecutorService tp = Executors.newCachedThreadPool();

//    public static void main(String[] args) throws IOException {
//        ServerSocket ss = new ServerSocket(80);
//        String str = "ASDFADF";
//        while(true) {
//            Socket s = ss.accept();
//            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            PrintWriter out = new PrintWriter(s.getOutputStream());
//            out.println("<html> <body> <p>" + str + "</p> </body> </html>");
//            out.close();
//            s.close();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//            byte[] nameNoise = new byte[8];
//            String name = "CLIENT_" + new Random().nextInt();
//
//            MulticastConfig config = MulticastConfig.builder()
//                    .group(SERVER_MULTICAST_GROUP)
//                    .message(SERVER_MULTICAST_MESSAGE)
//                    .port(SERVER_MULTICAST_PORT)
//                    .build();
//
//            Client client = Client.builder()
//                    .datagramInitExceptionHandler(System.out::println)
//                    .socketInitExceptionHandler(System.out::println)
//                    .multicastJoinExceptionHandler(System.out::println)
//                    .socketReceiveExceptionHandler(System.out::println)
//                    .socketSendExceptionHandler(System.out::println)
//                    .scheduledThreadPoolSupplier(() -> shtp)
//                    .build();
//
//            client.startListening(config, 0, 500, TimeUnit.MILLISECONDS);
//
//            client.run();
//    }

    public static String getAddressInLocalNetwork() {
        try(final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class ServerRunner {
        public static void main(String[] args) throws Exception {
            Server server = Server.builder()
                    .socketSendExceptionHandler(System.err::println)
                    .socketReceiveExceptionHandler(System.err::println)
                    .multicastJoinExceptionHandler(System.err::println)
                    .datagramInitExceptionHandler(System.err::println)
                    .socketInitExceptionHandler(System.err::println)
                    .scheduledThreadPoolSupplier(() -> shtp)
                    .threadPoolSupplier(() -> tp)
                    .serverPort(0)
                    .clientSocketAcceptor(s -> {
                        try {
                            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                            Object actualAgree = is.readObject();
                            System.out.println("Server got agreement object: " + actualAgree);
                            return actualAgree.equals(clientAgree);
                        } catch(IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .build();

            server.run();
            server.startAcceptingClientConnections();

            MulticastConfig serverMulConfig = MulticastConfig.builder()
                    .group(SERVER_MULTICAST_GROUP)
                    .message((SERVER_MULTICAST_MESSAGE + "$" + getAddressInLocalNetwork() + ":" + server.getServerPort()).getBytes())
                    .build();

            System.out.println("Server Running. Multicast message = " + new String(serverMulConfig.getMessage()));
            server.startPublishing(serverMulConfig, 0, 1, TimeUnit.SECONDS);
            server.startListening(serverMulConfig, 0, 500, TimeUnit.MILLISECONDS);

        }
    }

    public static String clientAgree = "CLIENT_AGREE";

    public static class ClientRunner {
        public static void main(String[] args) throws Exception {
            byte[] nameNoise = new byte[8];
            String name = "CLIENT_" + new Random().nextInt();

            MulticastConfig config = MulticastConfig.builder()
                    .group(SERVER_MULTICAST_GROUP)
                    .messageLength(SERVER_MULTICAST_MESSAGE.getBytes().length + 1000)
                    .build();

            Client client = Client.builder()
                    .socketSendExceptionHandler(System.err::println)
                    .socketReceiveExceptionHandler(System.err::println)
                    .multicastJoinExceptionHandler(System.err::println)
                    .datagramInitExceptionHandler(System.err::println)
                    .socketInitExceptionHandler(System.err::println)
                    .scheduledThreadPoolSupplier(() -> shtp)
                    .multicastPacketHandler((p, c) -> {
                        String actualMulticastMessage = new String(p.getData(), p.getOffset(), p.getLength());
                        System.out.println("CLIENT: received " + actualMulticastMessage + " from " + p.getSocketAddress());

                        int separatorIndex = actualMulticastMessage.indexOf("$");
                        String serverIP = actualMulticastMessage.substring(separatorIndex + 1);
                        actualMulticastMessage = actualMulticastMessage.substring(0, separatorIndex);
                        System.out.println("serverIP is " + serverIP);

                        String serverAddress = serverIP.split(":")[0];
                        String serverPort = serverIP.split(":")[1];

                        if(actualMulticastMessage.equals(SERVER_MULTICAST_MESSAGE))
                            System.out.println("Client checked server multicast message: ✔");
                        else
                            System.out.println("Client checked server multicast message: ❌");
                        try {
                            ((Client)c).connectToServer(new InetSocketAddress(InetAddress.getByName(serverAddress), Integer.valueOf(serverPort)));
                            c.sendToAll(clientAgree);
                            c.stopListening(config);
                            System.out.println("Client connected to Server");
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .build();

            client.startListening(config, 0, 500, TimeUnit.MILLISECONDS);

        }
    }
}
