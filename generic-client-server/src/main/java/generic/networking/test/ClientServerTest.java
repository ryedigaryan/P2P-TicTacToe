package generic.networking.test;

import generic.networking.common.MulticastConfig;
import generic.networking.endpoint.Client;
import generic.networking.endpoint.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public static ScheduledExecutorService tp = Executors.newScheduledThreadPool(3);

//    public static void main(String[] args) throws IOException {
//        ServerSocket ss = new ServerSocket(80);
//        String str = "QAQs utes";
//        while(true) {
//            Socket s = ss.accept();
//            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            PrintWriter out = new PrintWriter(s.getOutputStream());
//            out.println("<html> <body> <p>" + str + "</p> </body> </html>");
//            out.close();
//            s.close();
//        }
//    }

    public static void main(String[] args) throws Exception {
            byte[] nameNoise = new byte[8];
            String name = "CLIENT_" + new Random().nextInt();

            MulticastConfig config = MulticastConfig.builder()
                    .group(SERVER_MULTICAST_GROUP)
                    .message(SERVER_MULTICAST_MESSAGE)
                    .port(SERVER_MULTICAST_PORT)
                    .build();

            Client client = Client.builder()
                    .datagramInitExceptionHandler(System.out::println)
                    .multicastInitExceptionHandler(System.out::println)
                    .multicastJoinExceptionHandler(System.out::println)
                    .socketReceiveExceptionHandler(System.out::println)
                    .socketSendExceptionHandler(System.out::println)
                    .threadPoolSupplier(() -> tp)
                    .build();

            client.startListening(config, 0, 500, TimeUnit.MILLISECONDS);

            client.run();
    }

//    public static class ServerRunner {
//        public static void main(String[] args) throws Exception {
//            MulticastConfig serverMulConfig = MulticastConfig.builder()
//                    .group(SERVER_MULTICAST_GROUP)
//                    .message(SERVER_MULTICAST_MESSAGE)
//                    .port(SERVER_MULTICAST_PORT)
//                    .build();
//            Server server = Server.builder()
//                    .datagramInitExceptionHandler(System.out::println)
//                    .multicastInitExceptionHandler(System.out::println)
//                    .multicastJoinExceptionHandler(System.out::println)
//                    .socketReceiveExceptionHandler(System.out::println)
//                    .socketSendExceptionHandler(System.out::println)
//                    .threadPoolSupplier(() -> tp)
//                    .build();
//
//            server.startPublishing(serverMulConfig, 0, 1, TimeUnit.SECONDS);
//            server.startListening(serverMulConfig, 0, 500, TimeUnit.MILLISECONDS);
//
//            server.run();
//        }
//    }
//
//    public static class ClientRunner {
//        public static void main(String[] args) throws Exception {
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
//                    .multicastInitExceptionHandler(System.out::println)
//                    .multicastJoinExceptionHandler(System.out::println)
//                    .socketReceiveExceptionHandler(System.out::println)
//                    .socketSendExceptionHandler(System.out::println)
//                    .threadPoolSupplier(() -> tp)
//                    .build();
//
//            client.startListening(config, 0, 500, TimeUnit.MILLISECONDS);
//
//            client.run();
//        }
//    }
}
