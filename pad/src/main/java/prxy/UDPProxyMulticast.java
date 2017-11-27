package prxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class UDPProxyMulticast{

    private Properties multicastP;
    //private Properties unicastP;

    public UDPProxyMulticast() {

        multicastP = new Properties();

        try {
            multicastP.load(UDPProxyMulticast.class.getResourceAsStream("/proxy/multicast.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find properties file/s!");
        }
    }

    public void sendCommand(String command) {

        /*send to all nodes in the group command to count their connections and to send a message containing # of
        connections of the node, its UDP uni-cast host and port*/

        try {
            InetAddress group = InetAddress.getByName(multicastP.getProperty("group"));
            int port = Integer.parseInt(multicastP.getProperty("port"));

            DatagramSocket serverSocket = new DatagramSocket();

            DatagramPacket msgPacket = new DatagramPacket(command.getBytes(), command.getBytes().length, group, port);
            serverSocket.send(msgPacket);

            Thread.sleep(500);
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
