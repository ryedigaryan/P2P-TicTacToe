package generic.networking.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.function.Supplier;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class MulticastGroupUser implements Runnable {
    @NonNull private final MulticastSocket multicastSocket;
    @NonNull private final Supplier<byte[]> bufferSupplier;
    @NonNull private final InetAddress multicastGroup;
    @NonNull private final Integer multicastPort;
}
