package generic.networking.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.function.Supplier;

@Getter @Setter
public class MulticastGroupNotifier extends MulticastGroupUser {

    public MulticastGroupNotifier(MulticastSocket socket, Supplier<byte[]> multicastDataSupplier, InetAddress multicastGroup, Integer multicastPort) {
        super(socket, multicastDataSupplier, multicastGroup, multicastPort);
    }

    @Override
    public void run() {
        byte[] buffer = getBufferSupplier().get();
        DatagramPacket packet = new DatagramPacket(
                buffer,
                buffer.length,
                getMulticastGroup(),
                getMulticastPort()
        );

        try {
            if(Thread.currentThread().isInterrupted())
                return;
            getMulticastSocket().send(packet);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
