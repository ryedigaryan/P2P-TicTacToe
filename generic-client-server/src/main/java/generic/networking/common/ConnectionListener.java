package generic.networking.common;

import java.net.InetAddress;

public interface ConnectionListener {
    void foundEndPoint(InetAddress endPoint);
    void connectedToEndPoint(InetAddress endPoint);
}
