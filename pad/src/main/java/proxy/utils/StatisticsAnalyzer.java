package proxy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class StatisticsAnalyzer extends Thread{

    private List<String> statistics;
    private DataAggregator dataAggregator;
    private TCPMaven tcpMaven;

    public StatisticsAnalyzer() {

        this.dataAggregator = new DataAggregator();
        dataAggregator.start();
        this.statistics = new ArrayList<>();
    }

    @Override
    public void run() {

        while (statistics.size() == 0) {
            try {
                Thread.sleep(100);
                //System.out.println("Stats size : " + statistics.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Started processing statistics from Nodes...");

        int index = 0;

        for (int i = 0; i < statistics.size() - 1; i++) {

            int current = Integer.parseInt(statistics.get(i).split(":")[1]);
            int next = Integer.parseInt(statistics.get(i+1).split(":")[1]);

            if (next > current) {
                index = i + 1;
            }
        }

        String mavenHost = statistics.get(index).split(":")[2];
        Scanner in = new Scanner(statistics.get(index).split(":")[3]).useDelimiter("[^0-9]+");
        int mavenPort = in.nextInt();

        System.out.println("Identified maven, host " + mavenHost + ", port: " + mavenPort);

        tcpMaven = new TCPMaven(mavenHost, mavenPort);
        tcpMaven.start();
    }

    public void updateStatistics(String nodeStatistics) {

        System.out.println("Updated statistics.");
        statistics.add(nodeStatistics);
    }

    private void resetStatistics() {
        statistics = new ArrayList<>();
    }
}
