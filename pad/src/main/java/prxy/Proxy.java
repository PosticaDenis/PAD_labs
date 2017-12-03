package prxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class Proxy {

    //private UDPProxyMulticast udpProxyMulticast;
    //private UDPProxyUnicast udpProxyUnicast;
    private List<TCPProxyForClient> tcpProxyForClients;

    private static Properties unicastP;
    private static Properties multicastP;

    public Proxy(int port) {

        unicastP = new Properties();
        multicastP = new Properties();

        try {
            unicastP.load(UDPProxyMulticast.class.getResourceAsStream("/proxy/unicast.properties"));
            multicastP.load(UDPProxyMulticast.class.getResourceAsStream("/proxy/multicast.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not locate uni/multi-cast properties files!");
        }

        //udpProxyMulticast = new UDPProxyMulticast();

        //udpProxyUnicast = new UDPProxyUnicast();
        //udpProxyUnicast.start();

        tcpProxyForClients = new ArrayList<>();

        startClientListener(port);
    }

    private void startClientListener(int port) {

        System.out.println("Starting Listener ...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                TCPProxyForClient senderListener = new TCPProxyForClient(serverSocket.accept());
                tcpProxyForClients.add(senderListener);
                senderListener.start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
    }

    public static Properties getUnicastP() {
        return unicastP;
    }

    public static Properties getMulticastP() {
        return multicastP;
    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy(1234);
    }
}
