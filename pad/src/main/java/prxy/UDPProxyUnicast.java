package prxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Properties;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class UDPProxyUnicast extends Thread {

    private Properties unicastP;

    public UDPProxyUnicast() {

        unicastP = new Properties();

        try {
            unicastP.load(UDPProxyMulticast.class.getResourceAsStream("/proxy/unicast.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not locate uni-cast properties file!");
        }
    }

    @Override
    public void run() {

        try {

            int port = Integer.parseInt(unicastP.getProperty("port"));

            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];

            while(true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String sentence = new String( receivePacket.getData());

                /*if (sentence.contains("pong")) {   //message structure "pong::"
                    TCPProxyForClient.setNrOfNodes(TCPProxyForClient.getNrOfNodes() + 1);
                }*/
                if (sentence.contains("statistics")) {  //message structure "statistics:TCP_host:TCP_port"
                    StatisticsAnalyzer.updateStatistics(sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
