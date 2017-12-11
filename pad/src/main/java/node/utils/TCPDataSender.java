package node.utils;

import node.Node;
import node.TCPNode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Dennis on 11-Dec-17.
 **/
public class TCPDataSender extends Thread {

    private static String data = "data: Maven DATA";

    private String requestNodeId;

    private Node n;

    public TCPDataSender(Node n) {
        this.n = n;
    }

    public void setRequestNodeId(String requestNodeId) {
        this.requestNodeId = requestNodeId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendDataToProxy();
    }

    public void updateData(String update) {
        data += update;
    }

    private void send(TCPNode s, String cmd) {
        try {
            DataOutputStream out = new DataOutputStream(s.getNodeSocket().getOutputStream());
            out.writeBytes(cmd + "\r");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error sending command: " + cmd + " to the Nodes which are connected to the current Node.");
        }
    }

    public void collectDataFromNodes() {

        String cmd = "node:data:" + n.getNodeId();

        for (Map.Entry<String, TCPNode> e : n.getIsConnected().entrySet()) {
            String nodeId    = e.getKey();
            TCPNode tcpNode  = e.getValue();

            if (!nodeId.equals(requestNodeId)) {
                //System.out.println("Found Nodes which are connected to this Node.");
                send(tcpNode, cmd);
            }
            /*else {
                System.out.println("Found Nodes which are connected to this Node, but it's just the maven, so ignoring it.");
            }*/
        }

        for (Map.Entry<String, TCPNode> e : n.getConnectedTo().entrySet()) {
            String nodeId = e.getKey();
            //System.out.println("Found Nodes to which is connected current Node: " + nodeId);
            TCPNode tcpNode  = e.getValue();

            if (!nodeId.equals(requestNodeId)) {
                send(tcpNode, cmd);
            }
        }

    }

    private void sendDataToProxy() {
        send(n.getProxyConnection(), data);
    }

    public void sendDataToMaven() {

        String data = "data:DATA collected by Node(" + n.getNodeId() + ").";

        TCPNode isConnected = n.getIsConnected().get(requestNodeId);
        TCPNode connectedTo = n.getConnectedTo().get(requestNodeId);

        if (isConnected != null) {
            //System.out.println("The maven Node is connected to the Node from which data was requested.");
            send(isConnected, data);
        }
        else {
            //System.out.println("The Node from which data was requested is connected to the maven Node.");
            send(connectedTo, data);
        }
    }
}
