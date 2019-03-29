package generic.networking.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.function.Supplier;

@Getter @Setter
public class MulticastGroupListener extends MulticastGroupUser {

    public MulticastGroupListener(MulticastSocket socket, Supplier<byte[]> emptyDataBufferSupplier, InetAddress multicastGroup, Integer multicastPort) throws IOException {
        super(socket, emptyDataBufferSupplier, multicastGroup, multicastPort);
        getMulticastSocket().joinGroup(getMulticastGroup());
    }

    @NonNull
    private MulticastPacketHandler multicastPacketHandler;

    @Override
    public void run() {
        try {
            assert multicastPacketHandler != null : "multicastPacketHandler is null";
            final byte[] buffer = this.getBufferSupplier().get();
            final DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            getMulticastSocket().receive(receivedPacket);
            getMulticastPacketHandler().handlePacket(receivedPacket);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
