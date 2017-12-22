package proxy;

import proxy.utils.DataAggregator;
import proxy.utils.StatisticsAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class TCPProxyForClient extends Thread {

    private Socket cConnection;
    private UDPProxyMulticast udpProxyMulticast;
    private StatisticsAnalyzer statisticsAnalyzer;

    public TCPProxyForClient(Socket clientConnection) {
        this.cConnection = clientConnection;
        this.udpProxyMulticast = new UDPProxyMulticast();
        this.statisticsAnalyzer = new StatisticsAnalyzer();

        UDPProxyUnicast unicast = new UDPProxyUnicast(statisticsAnalyzer);
        unicast.start();
    }

    @Override
    public void run() {
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
                System.out.println("Something is wrong with TCP connection of the client.");
                removeThread();
            }
            cConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeThread() {
        Proxy.getTcpProxyForClients().remove(this);

    }
}
