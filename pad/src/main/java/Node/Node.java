package node;

import java.io.IOException;
import java.net.*;
import java.util.Properties;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Node {

    private UDPNode udpNode;
    private static Properties tcpProperties;

    public Node() {
        tcpProperties = new Properties();

        try {
            tcpProperties.load(Node.class.getResourceAsStream("/node/tcp.properties"));

            udpNode = new UDPNode();
            udpNode.start();

            while (true) {
                ServerSocket nodeSocket = new ServerSocket(Integer.parseInt(tcpProperties.getProperty("port")));

                TCPNode cHandler = new TCPNode(nodeSocket.accept());
                cHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Fu#k this sh#t, I'm out!");
    }

    public static Properties getTcpProperties() {
        return tcpProperties;
    }
}