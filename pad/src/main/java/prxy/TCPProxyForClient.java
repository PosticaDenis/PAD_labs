package prxy;

import proxy.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class TCPProxyForClient extends Thread {

    private static int nrOfNodes = 0;
    private Socket cConnection;
    private UDPProxyMulticast udpProxyMulticast;
    private StatisticsAnalyzer statisticsAnalyzer;

    public TCPProxyForClient(Socket clientConnection, UDPProxyMulticast udpProxyMulticast) {
        this.cConnection = clientConnection;
        this.udpProxyMulticast = udpProxyMulticast;
        this.statisticsAnalyzer = new StatisticsAnalyzer(nrOfNodes);
    }

    @Override
    public void run() {
        /*process Client commands*/
        statisticsAnalyzer.start();

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cConnection.getInputStream()));

            String received;
            try {
                while (true) {

                    received = in.readLine();
                    if (received != null) {
                        udpProxyMulticast.sendCommand("ping");

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

    public static void setNrOfNodes(int nrOfNodes) {
        TCPProxyForClient.nrOfNodes = nrOfNodes;
    }

    public static int getNrOfNodes() {
        return nrOfNodes;
    }
}