package proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Properties;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class ResponseProcessor extends Thread {

    @Override
    public void run() {

        //Properties mProperties = Proxy.getMulticastProperties();
        Properties uProperties = Proxy.getUnicastProperties();

        try {
            DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(uProperties.getProperty("port")));
            byte[] response = new byte[1024];

            while(true)  //waits for Nodes' reply for 5 seconds
            {
                DatagramPacket responsePacket = new DatagramPacket(response, response.length);
                serverSocket.receive(responsePacket);
                String reply = new String(responsePacket.getData());

                if (reply.contains("pong")) {
                    DataAggregator.setNodesCounter(DataAggregator.getNodesCounter() + 1);
                }
                else {
                    System.out.println("..........");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
