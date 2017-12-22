package node;

import node.utils.TCPDataSender;

import java.io.*;
import java.net.Socket;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class TCPNode extends Thread{

    private Socket nodeSocket;
    private Node n;
    private TCPDataSender dataSender;
    private DataOutputStream out;
    private String data = "";

    public TCPNode(Node n, Socket s) {
        this.nodeSocket = s;
        this.n = n;

        dataSender = new TCPDataSender(n);
    }

    /**
     * proxy:
     * node:
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));
            out = new DataOutputStream(nodeSocket.getOutputStream());

            String received;

            try {
                while (true) {
                    received = in.readLine();

                    if (received != null) {
                        if (received.contains("identify:proxy")) {

                            System.out.println("Proxy has connected to Maven Node.");
                            n.setProxyId(this);
                        }
                        if (received.startsWith("identify:node:")) {

                            n.setIsConnected(received.split(":")[2], this);
                            System.out.println("Node with ID: " + received.split(":")[2] + " has connected.");
                        }
                        if (received.startsWith("proxy:data")) {
                            System.out.println("Proxy required data from MAVEN.");
                            n.setState(true);

                            dataSender.collectDataFromNodes();
                            dataSender.start();
                        }
                        if (received.startsWith("node:data:")) {
                            System.out.println("Maven or another node required data.");

                            if (!n.isState()) {
                                n.setState(true);
                                String requestNodeId = received.split(":")[2];

                                dataSender.setRequestNodeId(requestNodeId);
                                dataSender.collectDataFromNodes();
                                dataSender.sendDataToMaven();
                            }
                            else {
                                System.out.println("Node already sent data. Ignoring request.");
                            }
                        }
                        if (received.startsWith("data`")) {

                            System.out.println("Node (" + n.getNodeId() + ") received data from another node: " + received.split("`")[1]);
                            dataSender.updateData(received.split("`")[1]);
                        }
                    }
                }
            } catch (IOException e) {
                //removeThread();
            }

            nodeSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getNodeSocket() {
        return nodeSocket;
    }

    public void send(String msg) {
        try {
            Thread.sleep(100);
            out.writeBytes(msg + "\r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
