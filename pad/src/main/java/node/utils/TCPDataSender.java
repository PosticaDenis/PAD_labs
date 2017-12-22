package node.utils;

import com.google.gson.reflect.TypeToken;
import node.Node;
import node.TCPNode;
import utils.Student;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dennis on 11-Dec-17.
 **/
public class TCPDataSender extends Thread {

    private String nodeData;
    private static String dat = "[]";
    private String requestNodeId;
    private Node n;

    public TCPDataSender(Node n) {
        this.n = n;
        nodeData = Node.dataJson.toJson(n.getData());
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

        nodeData = processAsJson(dat, nodeData);
        sendDataToProxy();
    }

    private String processAsJson(String update, String toUpdate) {

        Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
        List<Student> jsonData = Node.dataJson.fromJson(update, listType);
        jsonData.addAll(Node.dataJson.fromJson(toUpdate, listType));
        return Node.dataJson.toJson(jsonData);
    }

    public void updateData(String update) {
        dat = processAsJson(update, dat);
        System.out.println("data after update: " + dat);
    }

    private void send(TCPNode s, String cmd) {
        try {
            DataOutputStream out = new DataOutputStream(s.getNodeSocket().getOutputStream());
            out.writeBytes(cmd + "\r");
        } catch (IOException e) {
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
        System.out.println("Sending data to proxy: " + nodeData);
        send(n.getProxyConnection(), nodeData);
    }

    public void sendDataToMaven() {

        String data = "data`" + nodeData;

        TCPNode isConnected = n.getIsConnected().get(requestNodeId);
        TCPNode connectedTo = n.getConnectedTo().get(requestNodeId);

        if (isConnected != null) {
            //System.out.println("The maven Node is connected to the Node from which nodeData was requested.");
            send(isConnected, data);
        }
        else {
            //System.out.println("The Node from which nodeData was requested is connected to the maven Node.");
            send(connectedTo, data);
        }
    }
}
