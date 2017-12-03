package node;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Node {

    private UDPNode udpNode;
    private static Properties tcpProperties;
    private static List<TCPNode> connections;

    //private static int connections = 0;

    public Node() {
        tcpProperties = new Properties();
        connections = new ArrayList<>();

        try {
            tcpProperties.load(Node.class.getResourceAsStream("/node/tcp.properties"));

            udpNode = new UDPNode();
            udpNode.start();

            //System.out.println("here");
            ServerSocket nodeSocket = new ServerSocket(Integer.parseInt(tcpProperties.getProperty("port")));

            while (true) {
                TCPNode cHandler = new TCPNode(nodeSocket.accept());
                connections.add(cHandler);
                System.out.println("Someone connected through TCP");
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

    public static int getConnectionsSize() {
        return connections.size();
    }

    public static void main(String[] args) {
        Node n = new Node();
    }
}