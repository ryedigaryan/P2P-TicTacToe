package generic.networking.common;

import generic.networking.endpoint.Endpoint;

import java.net.DatagramPacket;

public interface MulticastPacketHandler {
    void handlePacket(DatagramPacket packet, Endpoint receiver);
}
