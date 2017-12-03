package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Properties;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class UDPNode extends Thread {

    private final static String INET_ADDR = "228.5.6.7";
    private final static int PORT = 5000;

    @Override
    public void run() {

        byte[] buf = new byte[256];

        try {
            MulticastSocket clientSocket = new MulticastSocket(PORT);

            InetAddress address = InetAddress.getByName(INET_ADDR);

            clientSocket.joinGroup(address);

            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);

                if (!msg.isEmpty()) {

                    System.out.println("UDP multicast: received command: " + msg);
                    sendResponse(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String msg) {

        String response;
        Properties tcpProperties = Node.getTcpProperties();

        String command = msg.split(":")[0];
        String proxyUdpUnicastHost = msg.split(":")[1];
        int proxyUdpUnicastPort = 7777;//Integer.parseInt(msg.split(":")[2]);

        if (command.equals("statistics")) {
            System.out.println("Proxy required statistics.");
            String nrOfConnections = String.valueOf(Node.getConnectionsSize());
            response = "statistics:" + nrOfConnections + ":" + tcpProperties.getProperty("host") + ":" + tcpProperties.getProperty("port");

            System.out.println("Node sent stats: " + response);
        }
        else {
            response = "pong";
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
