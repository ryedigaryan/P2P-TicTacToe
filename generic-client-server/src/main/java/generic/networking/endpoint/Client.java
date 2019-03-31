package generic.networking.endpoint;

import generic.networking.common.MulticastPacketHandler;
import generic.networking.common.exception.NoSuchEndpointException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class Client extends AbstractEndpoint implements Closeable {

    private InetSocketAddress serverAddress;
    private Socket socket;
    private ObjectInputStream serverInStream;
    private ObjectOutputStream serverOutStream;


    @Builder
    private Client(Supplier<ScheduledExecutorService> threadPoolSupplier, Consumer<SocketException> datagramInitExceptionHandler, Consumer<IOException> multicastInitExceptionHandler, Consumer<IOException> multicastJoinExceptionHandler, Consumer<IOException> socketSendExceptionHandler, Consumer<IOException> socketReceiveExceptionHandler, MulticastPacketHandler multicastPacketHandler) {
        super(threadPoolSupplier, multicastPacketHandler, datagramInitExceptionHandler, multicastInitExceptionHandler, multicastJoinExceptionHandler, socketSendExceptionHandler, socketReceiveExceptionHandler);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Endpoint interface overridden methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Set<InetSocketAddress> getConnectedEndpoints() {
        // I hope JIT will optimize this 😁
        return Collections.singleton(serverAddress);
    }

    public void connectToServer(InetSocketAddress serverAddress) throws IOException {
        if(getSocket() != null)
            throw new IllegalStateException("Client already connected to server");
        setSocket(new Socket(serverAddress.getAddress(), serverAddress.getPort()));
        setServerInStream(new ObjectInputStream(getSocket().getInputStream()));
        setServerOutStream(new ObjectOutputStream(getSocket().getOutputStream()));
        setServerAddress(serverAddress);
    }

    public void sendToAll(Serializable o) throws IOException, NoSuchEndpointException {
        checkSocket();
        assert getServerOutStream() != null : "if socket to server is not null then output stream may not be null";
        getServerOutStream().writeObject(o);
    }

    @Override
    public void send(Serializable object, InetSocketAddress serverAddress) throws NoSuchEndpointException, IOException {
        checkSocket();
        ensureServerExists(serverAddress);
        getServerOutStream().writeObject(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T receive(InetSocketAddress endpointAddress) throws NoSuchEndpointException, IOException, ClassNotFoundException {
        checkSocket();
        ensureServerExists(endpointAddress);
        return (T)getServerInStream().readObject();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Closeable interface overridden methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void close() throws IOException {
        if(getSocket() != null) {
            getServerInStream().close();
            getServerOutStream().close();
            getSocket().close();

            setServerInStream(null);
            setServerOutStream(null);
            setSocket(null);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // helper methods
    ///////////////////////////////////////////////////////////////////////////

    private void checkSocket() {
        if(getSocket() == null) {
            throw new NoSuchElementException("Socket to Server not configured");
        }
    }

    // may be this should also check connectivity??????
    private void ensureServerExists(InetSocketAddress endpointAddress) throws NoSuchEndpointException {
        if(!getServerAddress().equals(endpointAddress))
            throw new NoSuchEndpointException(endpointAddress);
    }
}
