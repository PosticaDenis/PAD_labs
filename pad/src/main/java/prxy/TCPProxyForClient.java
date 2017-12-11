package prxy;

import prxy.utils.DataAggregator;
import prxy.utils.StatisticsAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class TCPProxyForClient extends Thread {

    //private static int nrOfNodes = 0;

    private Socket cConnection;
    private UDPProxyMulticast udpProxyMulticast;
    private UDPProxyUnicast udpProxyUnicast;
    private StatisticsAnalyzer statisticsAnalyzer;

    public TCPProxyForClient(Socket clientConnection) {
        this.cConnection = clientConnection;
        this.udpProxyMulticast = new UDPProxyMulticast();

        this.statisticsAnalyzer = new StatisticsAnalyzer();

        this.udpProxyUnicast = new UDPProxyUnicast(statisticsAnalyzer);
        udpProxyUnicast.start();
    }

    @Override
    public void run() {
        /*process Client commands*/
        //statisticsAnalyzer.start();

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cConnection.getInputStream()));

            String received;
            try {
                while (true) {

                    received = in.readLine();
                    if (received != null) {
                        System.out.println("Received command from client: " + received);
                        udpProxyMulticast.sendCommand("statistics");

                        try {
                            Thread.sleep(750);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        statisticsAnalyzer.start();
                        DataAggregator.setCommand(received);
                    }
                }
            } catch (IOException e) {
                //removeThread();
            }

            in.close();
            cConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // public static void setNrOfNodes(int nrOfNodes) {
   //     TCPProxyForClient.nrOfNodes = nrOfNodes;
   // }

   /* public static int getNrOfNodes() {
        return nrOfNodes;
    }*/
}
