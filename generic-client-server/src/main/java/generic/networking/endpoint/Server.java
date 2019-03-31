package generic.networking.endpoint;


import generic.networking.common.MulticastPacketHandler;
import generic.networking.common.exception.NoSuchEndpointException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Server extends AbstractEndpoint implements Runnable {

    @Getter(AccessLevel.PRIVATE)
    private ObjectIOStreamPool objectIOStreamPool = new ObjectIOStreamPool();

    @Getter(AccessLevel.PRIVATE)
    private Map<InetSocketAddress, Socket> clientAddress2Socket = new HashMap<>();

    @NonNull
    @Getter
    private Supplier<ExecutorService> threadPoolSupplier;

    @NonNull
    @Getter @Setter
    private Predicate<Socket> clientSocketAcceptor;

    @Builder
    public Server(Supplier<ScheduledExecutorService> scheduledThreadPoolSupplier, Consumer<SocketException> datagramInitExceptionHandler, Consumer<IOException> socketInitExceptionHandler, Consumer<IOException> multicastJoinExceptionHandler, Consumer<IOException> socketSendExceptionHandler, Consumer<IOException> socketReceiveExceptionHandler, MulticastPacketHandler multicastPacketHandler, Supplier<ExecutorService> threadPoolSupplier, Integer serverPort, Predicate<Socket> clientSocketAcceptor) {
        super(scheduledThreadPoolSupplier, multicastPacketHandler, datagramInitExceptionHandler, socketInitExceptionHandler, multicastJoinExceptionHandler, socketSendExceptionHandler, socketReceiveExceptionHandler);
        this.threadPoolSupplier = Objects.requireNonNull(threadPoolSupplier, "scheduledThreadPoolSupplier may not be null");
        this.serverPort = Objects.requireNonNull(serverPort, "Server port may not be null");
        this.clientSocketAcceptor = Objects.requireNonNull(clientSocketAcceptor);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AbstractEndpoint overridden methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Set<InetSocketAddress> getConnectedEndpoints() {
        return getClientAddress2Socket().keySet();
    }

    @Override
    public void send(Serializable object, InetSocketAddress clientAddress) throws NoSuchEndpointException, IOException {
        ensureClientExists(clientAddress);
        getObjectIOStreamPool().getObjectOutputStream(clientAddress)
                .writeObject(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T receive(InetSocketAddress clientAddress) throws NoSuchEndpointException, IOException, ClassNotFoundException {
        ensureClientExists(clientAddress);
        return (T) getObjectIOStreamPool().getObjectInputStream(clientAddress)
                .readObject();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Runnable#run()
    ///////////////////////////////////////////////////////////////////////////

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private ServerSocket serverSocket;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private Integer serverPort;

    private static final int CONNECTION_WAIT_TIMEOUT = 3000;

    @Override
    public void run() {
        try {
            assert getServerPort() != null : "Server port may not be null";
            setServerSocket(new ServerSocket(getServerPort()));
            getServerSocket().setSoTimeout(CONNECTION_WAIT_TIMEOUT);
        } catch (IOException e) {
            getSocketInitExceptionHandler().accept(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Server's methods
    ///////////////////////////////////////////////////////////////////////////

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private Future<?> socketAcceptorFuture;

    public void startAcceptingClientConnections() {
        Objects.requireNonNull(getServerSocket(), "Server Socket may not be null");
        socketAcceptorFuture = getScheduledThreadPoolSupplier().get().submit(() -> {
            while (true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("SERVER: No more client connection will be accepted");
                    return null;
                }
                Socket clientSocket;
                try {
                    clientSocket = getServerSocket().accept();
                    if (getClientSocketAcceptor().test(clientSocket)) {
                        getClientAddress2Socket().put((InetSocketAddress) clientSocket.getLocalSocketAddress(), clientSocket);
                    }
                } catch (SocketTimeoutException e) {
                    // ok, continue the endless loop,
                    // if the thread is interrupted then exit the loop
                }
            }
        });
    }

    public void stopAcceptingClientConnections() {
        if(getSocketAcceptorFuture() == null)
            throw new IllegalStateException("Socket acceptor has already been stopped");
        getSocketAcceptorFuture().cancel(true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper methods
    ///////////////////////////////////////////////////////////////////////////

    // may be this also should check connectivity??????????
    private void ensureClientExists(InetSocketAddress clientAddress) throws NoSuchEndpointException {
        if(getClientAddress2Socket().isEmpty())
            throw new NoSuchEndpointException("No client is connected");
        if(!getClientAddress2Socket().containsKey(clientAddress))
            throw new NoSuchEndpointException("No such client: " + clientAddress);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper Inner Classes
    ///////////////////////////////////////////////////////////////////////////

    private class ObjectIOStreamPool {
        private Map<InetSocketAddress, ObjectInputStream> objectInStreams = new HashMap<>();
        private Map<InetSocketAddress, ObjectOutputStream> objectOutStreams = new HashMap<>();

        ObjectInputStream getObjectInputStream(InetSocketAddress clientAddress) {
            return objectInStreams.computeIfAbsent(
                    clientAddress,
                    k -> {
                        try {
                            return new ObjectInputStream(getClientAddress2Socket().get(clientAddress).getInputStream());
                        } catch (IOException e) {
                            getSocketReceiveExceptionHandler().accept(e);
                            return null;
                        }
                    }
            );
        }

        ObjectOutputStream getObjectOutputStream(InetSocketAddress clientAddress) {
            return objectOutStreams.computeIfAbsent(
                    clientAddress,
                    k -> {
                        try {
                            return new ObjectOutputStream(getClientAddress2Socket().get(clientAddress).getOutputStream());
                        } catch (IOException e) {
                            getSocketSendExceptionHandler().accept(e);
                            return null;
                        }
                    }
            );
        }

    }
}
