package node;

import utils.RandomString;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Node {

    private int connections = 0;
    //private List<NodeConnector> nodeConnectors;
    private List<String> connectedNodes;
    private InetAddress nodeIPAddress;
    private int port;

    private Socket connectionSocket;
    //private
    private static String nodeId;

    private ServerSocket serverNodeSocket;

    public Node(int port) {

        this.port = port;
        try {
            this.nodeIPAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        connectedNodes = new ArrayList<>();             //list of node to which this Node is connected
        nodeId = new RandomString().nextString();         //Node identificator generation

        System.out.println("Starting node connector ...");

        try {
            DatagramSocket nodeSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];

            while(true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                nodeSocket.receive(receivePacket);
                String sentence = new String( receivePacket.getData());
                System.out.println("Received a new registration: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //it simulates the connection to other nodes, as it just sends an UDP message to other nodes
    //saying them that he is connected to them. It can be said that we just subscribe to Nodes.
    public void connect(List<Node> nodes) {

        List<Node> actualNodesToConnect = new ArrayList<>();

        for (Node node: nodes) {

            String nodeId = node.getNodeId();

            if (!connectedNodes.contains(nodeId)) {
                connectedNodes.add(nodeId);
                actualNodesToConnect.add(node);
            }
            else {
                System.out.println("Node is already connected to node " + node.getNodeId() + "!");
            }
        }

        for (Node node: actualNodesToConnect) {

            try {
                DatagramSocket clientSocket = new DatagramSocket();
                byte[] sendData = this.getNodeId().getBytes();//node.getNodeId().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, node.getNodeIPAddress(), node.getPort());
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int getPort() {
        return port;
    }

    public InetAddress getNodeIPAddress() {
        return nodeIPAddress;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getConnectionsCnt() {
        return connectedNodes.size();
    }
}