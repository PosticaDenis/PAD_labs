package proxy;

import proxy.utils.StatisticsAnalyzer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class UDPProxyUnicast extends Thread {

    private StatisticsAnalyzer sAnalyzer;

    public UDPProxyUnicast(StatisticsAnalyzer sAnalyzer) {

        this.sAnalyzer = sAnalyzer;
    }

    @Override
    public void run() {
        try {
            int port = Integer.parseInt(Proxy.getUnicastP().getProperty("port"));

            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            while(true)
            {

                serverSocket.receive(receivePacket);

                String stat = new String(receivePacket.getData());

                if (stat.contains("statistics")) {  //message structure "statistics:TCP_host:TCP_port"
                    System.out.println("Received stats: " + stat);
                    sAnalyzer.updateStatistics(stat);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
