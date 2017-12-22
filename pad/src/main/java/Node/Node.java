package node;

import com.google.gson.Gson;
import utils.RandomString;
import utils.Student;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Node extends Thread{

    private UDPNode udpNode;

    private Map<String, TCPNode> connectedTo;
    private Map<String, TCPNode> isConnected;

    private String tcpHost = "localhost";
    private int tcpPort;

    private String id;
    private TCPNode proxyConnection;

    private ServerSocket nodeSocket;

    private List<Student> data = new ArrayList<>();
    public static Gson dataJson = new Gson();

    private boolean state = false;

    public Node() {

        connectedTo = new HashMap<>();
        id = new RandomString().nextString();
        isConnected = new HashMap<>();

        try {
            nodeSocket = new ServerSocket(0);

            tcpPort = nodeSocket.getLocalPort();
            //System.out.println("For Node: " + id + " system chose port " + nodeSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            udpNode = new UDPNode(this);
            udpNode.start();

            while (true) {
                TCPNode cHandler = new TCPNode(this, nodeSocket.accept());
                //System.out.println("Someone connected through TCP");
                cHandler.start();
            }
        } catch (IOException e) {
            System.out.println("I'm out!");
            e.printStackTrace();
            return;
        }
    }

    public void connectTo(Node node) {
        System.out.println("Connecting to node " + node.getNodeId());

        String nodeHost = node.getTcpHost();
        int nodePort = node.getTcpPort();
        String nodeId = node.getNodeId();

        if (nodeId.equals(id)) {

            System.out.println("You are trying to connect a Node to itself. Not cool!");
            return;
        }

        if (connectedTo.containsKey(nodeId)) {

            System.out.println("You are trying to connect to the same Node multiple times. Not cool!");
            return;
        }

        if (isConnected.containsKey(nodeId)) {

            System.out.println("The node to which you are trying to connect is already connected to the current Node. Not cool!");
            return;
        }

        try {
            Socket nodeS = new Socket(nodeHost, nodePort);

            TCPNode tcpNode = new TCPNode(this, nodeS);
            tcpNode.start();

            String identification = "identify:node:" + id + "\r";
            tcpNode.send(identification);

            this.connectedTo.put(nodeId, tcpNode);
        } catch (IOException e) {

            System.out.println("Connection of the current node to another node failed. Maybe the node you are trying to connect is not yet started.");
            e.printStackTrace();
        }
    }

    public String getTcpHost() {
        return tcpHost;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getConnectionsSize() {
        return connectedTo.size() + isConnected.size();
    }

    public void setIsConnected(String key, TCPNode value) {
        isConnected.put(key, value);
    }

    public String getNodeId() {
        return id;
    }

    public TCPNode getProxyConnection() {
        return proxyConnection;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setProxyId(TCPNode proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public Map<String, TCPNode> getConnectedTo() {
        return connectedTo;
    }

    public Map<String, TCPNode> getIsConnected() {
        return isConnected;
    }

    public static void main(String[] args) {
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();


        List<Student> students = new ArrayList<>();
        students.add(new Student("John",18));
        students.add(new Student("Sully",19));
        n1.setData(students);

        List<Student> students1 = new ArrayList<>();
        students1.add(new Student("Mark",18));
        students1.add(new Student("Molly",19));
        n2.setData(students1);

        List<Student> students2 = new ArrayList<>();
        students2.add(new Student("Carl",18));
        students2.add(new Student("Bob",19));
        n3.setData(students2);

        n1.start();
        n2.start();
        n3.start();
        n4.start();
        n5.start();
        n6.start();

        n2.connectTo(n1);
        n3.connectTo(n1);
        n3.connectTo(n2);
        n4.connectTo(n2);
        n5.connectTo(n3);


    }

    public  List<Student> getData() {
        return data;
    }

    public void updateNodeData(Student data) {
        this.data.add(data);
    }

    public void setData(List<Student> data) {
        this.data = data;
    }
}