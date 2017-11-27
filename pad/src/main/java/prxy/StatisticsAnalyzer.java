package prxy;

import proxy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class StatisticsAnalyzer extends Thread {

    private static List<String> statistics = new ArrayList<>();
    private int nrOfNodes;
    private DataAggregator dataAggregator;

    public StatisticsAnalyzer(int nrOfNodes) {

        this.nrOfNodes = nrOfNodes;
    }

    @Override
    public void run() {

        while(true) {
            if (statistics.size() == nrOfNodes && nrOfNodes != 0) {
                System.out.println("Processing statistics...");

                TCPProxyForClient.setNrOfNodes(0);

                getMavenTCPData();

                resetStatistics();
            }
        }
    }

    private String getMavenTCPData() {

        String maven = statistics.get(0);
        for (int i = 0; i < statistics.size() - 1; i++) {

            int current = Integer.parseInt(statistics.get(0).split(":")[i]);
            int next = Integer.parseInt(statistics.get(0).split(":")[i+1]);

            if (next > current) {
                maven = statistics.get(i);
            }
        }
        return maven;
    }

    public static void updateStatistics(String nodeStatistics) {
        statistics.add(nodeStatistics);
    }

    private void resetStatistics() {
        statistics = new ArrayList<>();
    }
}
