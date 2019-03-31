package generic.networking.endpoint;

import generic.networking.common.MulticastConfig;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

public interface EndPoint extends Runnable {

    @Override
    void run();

    void startPublishing(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit);

    void stopPublishing(MulticastConfig config);

    void startListening(MulticastConfig config, int initialDelay, int delay, TimeUnit delayUnit);

    void stopListening(MulticastConfig config);
}
