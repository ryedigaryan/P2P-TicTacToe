package generic.networking.endpoint;

import generic.common.Tuple;
import generic.networking.common.MulticastConfig;
import generic.networking.common.MulticastPacketHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter @Setter
@RequiredArgsConstructor
public abstract class AbstractEndpoint implements Endpoint {

    @Setter(AccessLevel.NONE)
    private Map<MulticastConfig, Tuple<MulticastPublisher, ScheduledFuture<?>>> publishersMap = new HashMap<>();
    @Setter(AccessLevel.NONE)
    private Map<MulticastConfig, Tuple<MulticastListener, ScheduledFuture<?>>> listenersMap = new HashMap<>();

    @NonNull private Supplier<ScheduledExecutorService> scheduledThreadPoolSupplier;
    @NonNull private MulticastPacketHandler multicastPacketHandler;

    // TODO: 3/30/2019 refactor this shit
    @NonNull private Consumer<? super SocketException> datagramInitExceptionHandler;
    @NonNull private Consumer<? super IOException> socketInitExceptionHandler;
    @NonNull private Consumer<? super IOException> multicastJoinExceptionHandler;
    @NonNull private Consumer<? super IOException> socketSendExceptionHandler;
    @NonNull private Consumer<? super IOException> socketReceiveExceptionHandler;

    @Override
    public void startPublishing(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit) {
        MulticastPublisher publisher = new MulticastPublisher(config);
        ScheduledFuture<?> publisherFuture = getScheduledThreadPoolSupplier().get()
                .scheduleWithFixedDelay(publisher, initialDelay, delay, delayUnit);
        // save the publisher and the config for later use
        getPublishersMap().put(config, Tuple.of(publisher, publisherFuture));
    }

    @Override
    public void stopPublishing(MulticastConfig config) {
        final Tuple<MulticastPublisher, ScheduledFuture<?>> publisherWithFuture = getPublishersMap().get(config);

        // we can interrupt running publisher, because it merely sends packet to predefined multicast group
        publisherWithFuture.getSecond()
                .cancel(true);
        // here we close the socket by which publisher was sending packets
        publisherWithFuture.getFirst()
                .getPublishSocket().close();
        // cleanup
        getPublishersMap().remove(config);
    }

    @Override
    public void startListening(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit) {
        assert getMulticastPacketHandler() != null : "Multicast packet handler may not be null";
        MulticastListener listener = new MulticastListener(p -> getMulticastPacketHandler().handlePacket(p, this), config);
        ScheduledFuture<?> listenerFuture = getScheduledThreadPoolSupplier().get()
                .scheduleWithFixedDelay(listener, initialDelay, delay, delayUnit);
        // save the listener and the config for later use
        getListenersMap().put(config, Tuple.of(listener, listenerFuture));
    }

    @Override
    public void stopListening(MulticastConfig config) {
        final Tuple<MulticastListener, ScheduledFuture<?>> listenerWithFuture = getListenersMap().get(config);

        // we can interrupt running listener, because it merely receives packets from predefined multicast group
        listenerWithFuture.getSecond()
                .cancel(true);
        // here we close the socket by which publisher was sending packets
        listenerWithFuture.getFirst()
                .getListenSocket().close();
        // cleanup
        getListenersMap().remove(config);
    }

    // TODO: 3/30/2019   MulticastPublisher and MulticastListener should be refactored to
    // TODO: 3/30/2019   reuse already created Sockets instead of creating new socket every time

    @Getter
    private class MulticastPublisher implements Runnable {
        private final DatagramSocket publishSocket;
        private final DatagramPacket publishPacket;

        MulticastPublisher(MulticastConfig config) {
            DatagramSocket socket;
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                getDatagramInitExceptionHandler().accept(e);
                socket = null;
            }

            publishSocket = socket;
            publishPacket = new DatagramPacket(config.getMessage(), config.getMessageLength(), config.getGroup());
        }

        private boolean canPublish() {
            return getPublishSocket() != null && getPublishPacket() != null;
        }

        @Override
        public void run() {
            try {
                if (canPublish()) {
                    publishSocket.send(publishPacket);
                }
            } catch (IOException e) {
                getSocketSendExceptionHandler().accept(e);
            }
        }
    }

    @Getter
    private class MulticastListener implements Runnable {
        private final MulticastSocket listenSocket;

        private final Consumer<DatagramPacket> receivedMessageConsumer;

        @Getter(AccessLevel.PRIVATE)
        private final Supplier<byte[]> emptyBufferSupplier;


        MulticastListener(Consumer<DatagramPacket> receivedMessageConsumer, MulticastConfig config) {
            // init multicast socket
            MulticastSocket socket;
            try {
                socket = new MulticastSocket(config.getGroup().getPort());
            } catch (IOException e) {
                getSocketInitExceptionHandler().accept(e);
                socket = null;
            }
            listenSocket = socket;

            // join multicast group
            if(listenSocket != null) {
                try {
                    listenSocket.joinGroup(config.getGroup().getAddress());
                } catch (IOException e) {
                    getMulticastJoinExceptionHandler().accept(e);
                }
            }

            // init remaining fields
            this.receivedMessageConsumer = receivedMessageConsumer;
            emptyBufferSupplier = config.emptyBufferSupplier();
        }

        @Override
        public void run() {
            byte[] emptyBuffer = getEmptyBufferSupplier().get();
            DatagramPacket packet = new DatagramPacket(emptyBuffer, emptyBuffer.length);
            try {
                getListenSocket().receive(packet);
                getReceivedMessageConsumer().accept(packet);
            } catch (IOException e) {
                getSocketReceiveExceptionHandler().accept(e);
            }
        }
    }
}
