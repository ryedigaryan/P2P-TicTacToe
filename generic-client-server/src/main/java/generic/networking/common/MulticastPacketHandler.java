package generic.networking.common;

import java.net.DatagramPacket;

public interface MulticastPacketHandler {
    void handlePacket(DatagramPacket packet);
}
