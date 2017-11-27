package prxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class Proxy {

    private UDPProxyMulticast udpProxyMulticast;
    private UDPProxyUnicast udpProxyUnicast;
    private List<TCPProxyForClient> tcpProxyForClients;

    public Proxy(int port) {


        udpProxyMulticast = new UDPProxyMulticast();

        udpProxyUnicast = new UDPProxyUnicast();
        udpProxyUnicast.start();

        startClientListener(port);
    }

    private void startClientListener(int port) {

        System.out.println("Starting Listener ...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                TCPProxyForClient senderListener = new TCPProxyForClient(serverSocket.accept(), udpProxyMulticast);
                tcpProxyForClients.add(senderListener);
                senderListener.start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
    }
}
