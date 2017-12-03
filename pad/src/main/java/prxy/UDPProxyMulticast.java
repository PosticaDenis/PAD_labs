package prxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class UDPProxyMulticast{

    /**
     * @param command
     */

    public void sendCommand(String command) {

        String unicastHost = Proxy.getUnicastP().getProperty("host");
        String unicastPort = Proxy.getUnicastP().getProperty("port");
        command = command + ":" + unicastHost + ":" + unicastPort;

        try {
            InetAddress group = InetAddress.getByName(Proxy.getMulticastP().getProperty("group"));
            int port = Integer.parseInt(Proxy.getMulticastP().getProperty("port"));

            DatagramSocket serverSocket = new DatagramSocket();

            DatagramPacket msgPacket = new DatagramPacket(command.getBytes(), command.getBytes().length, group, port);
            serverSocket.send(msgPacket);

            Thread.sleep(500);
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
