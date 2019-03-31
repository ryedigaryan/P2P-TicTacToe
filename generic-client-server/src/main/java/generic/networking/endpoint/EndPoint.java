package generic.networking.endpoint;

import generic.networking.common.MulticastConfig;
import generic.networking.common.exception.NoSuchEndpointException;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Endpoint {

    void startPublishing(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit);

    void stopPublishing(MulticastConfig config);

    void startListening(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit);

    void stopListening(MulticastConfig config);

    Set<InetSocketAddress> getConnectedEndpoints();

    /**
     * Send an object to all connected endpoints.
     * @param object the object to be sent
     * @throws NoSuchEndpointException if there is no connected endpoint
     */
    default void sendToAll(Serializable object) throws IOException, NoSuchEndpointException {
        for (InetSocketAddress connectedEndpoint : getConnectedEndpoints()) {
            send(object, connectedEndpoint);
        }
    }

    /**
     * Send an object to concrete endpoint.
     * @param object the object to be sent
     * @param endpointAddress the endpoint whom object will be sent
     * @throws NoSuchEndpointException if there is no such connected endpoint
     */
    void send(Serializable object, InetSocketAddress endpointAddress) throws NoSuchEndpointException, IOException;

    /**
     * Receive object from predefined endpoint.
     * @param endpointAddress the address of endpoint from which object will be received
     * @return the object sent by other endpoint.
     * @throws NoSuchEndpointException if there is no such connected endpoint
     */
    <T> T receive(InetSocketAddress endpointAddress) throws NoSuchEndpointException, IOException, ClassNotFoundException;
}
