package generic.networking.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

@Getter @Setter
public class MulticastConfig {
    private final InetSocketAddress group;
    private final byte[] message;
    private final int messageLength;

    @Builder
    public MulticastConfig(InetSocketAddress group, byte[] message, Integer messageLength) {
        if(message == null || message.length == 0) {
            if(messageLength == null)
                throw new IllegalArgumentException("If message is null or empty then messageLength may not be null");
        }
        this.messageLength = messageLength == null ? message.length : messageLength;

        this.group = Objects.requireNonNull(group, "Multicast group may not be null");
        this.message = message;
    }

    // a cached Supplier to not create new instance on each emptyBufferSupplier() call
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private Supplier<byte[]> emptyBufferSupplier;

    // may be it is possible to make some shared utility class which will supply empty buffers as well as other
    // useful staff
    public Supplier<byte[]> emptyBufferSupplier() {
        if(emptyBufferSupplier == null) {
            emptyBufferSupplier = () -> new byte[messageLength];
        }
        return emptyBufferSupplier;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof MulticastConfig))
            return false;

        final MulticastConfig other = (MulticastConfig)obj;

        if(!Objects.equals(this.getGroup(), other.getGroup()))
            return false;
        return Arrays.equals(this.getMessage(), other.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, message);
    }
}
