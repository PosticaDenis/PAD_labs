package node;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class UDPNode extends Thread {

    private final static String INET_ADDR = "228.5.6.7";
    private final static int PORT = 5000;
    private Node n;

    public UDPNode(Node n) {
        this.n = n;
    }

    @Override
    public void run() {

        byte[] buf = new byte[256];

        try {
            MulticastSocket clientSocket = new MulticastSocket(null);

            InetAddress address = InetAddress.getByName(INET_ADDR);

            SocketAddress sockAddr = new InetSocketAddress(PORT);
            clientSocket.bind(sockAddr);
            clientSocket.joinGroup(address);

            clientSocket.setTimeToLive(1);

            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);

                if (!msg.isEmpty() && msg.contains("statistics")) {
                    sendResponse(msg);
                }
                else {
                    System.out.println("Ignoring unknown UDP multi-cast command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String msg) {

        String response;

        String command = msg.split(":")[0];
        String proxyUdpUnicastHost = msg.split(":")[1];

        Scanner in = new Scanner(msg.split(":")[2]).useDelimiter("[^0-9]+");
        int proxyUdpUnicastPort = in.nextInt();

        //int proxyUdpUnicastPort = 7777;//Integer.parseInt(msg.split(":")[2]);

        if (command.equals("statistics")) {
            System.out.println("Proxy required stats.");

            String nrOfConnections = String.valueOf(n.getConnectionsSize());
            response = "statistics:" + nrOfConnections + ":" + n.getTcpHost() + ":" + String.valueOf(n.getTcpPort());

            System.out.println("Node with ID " + n.getNodeId() + " sent following stats: -> " + response);

            n.setState(false);
        }
        else {
            return;
        }

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(proxyUdpUnicastHost);

            byte[] responseBytes = response.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(responseBytes, responseBytes.length, IPAddress, proxyUdpUnicastPort);
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
