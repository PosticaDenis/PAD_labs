package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Properties;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class MulticastHandler extends Thread {
    private Properties multicastProperties;

    public MulticastHandler(Properties multicastProperties) {
        this.multicastProperties = multicastProperties;
    }

    /*@Override
    public void start() {

        String msg = multicastProperties.getProperty("host") + ":" + unicastPort;

        try {
            InetAddress group = InetAddress.getByName(multicastGroup);

            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);

            DatagramPacket communicate = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
            multicastSocket.send(communicate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
