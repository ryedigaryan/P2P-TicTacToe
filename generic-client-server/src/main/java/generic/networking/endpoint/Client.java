package generic.networking.endpoint;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
public class Client extends AbstractEndPoint {

    @Builder
    public Client(Supplier<ScheduledExecutorService> threadPoolSupplier, Consumer<SocketException> datagramInitExceptionHandler, Consumer<IOException> multicastInitExceptionHandler, Consumer<IOException> multicastJoinExceptionHandler, Consumer<IOException> socketSendExceptionHandler, Consumer<IOException> socketReceiveExceptionHandler) {
        super(threadPoolSupplier, datagramInitExceptionHandler, multicastInitExceptionHandler, multicastJoinExceptionHandler, socketSendExceptionHandler, socketReceiveExceptionHandler);
    }

    @Override
    protected void acceptMulticastMessage(DatagramPacket receivedPacket) {
        System.out.println("Client Received: " + new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength()));
    }

    @Override
    public void run() {
        System.out.println("CLIENT is running");
    }
}
