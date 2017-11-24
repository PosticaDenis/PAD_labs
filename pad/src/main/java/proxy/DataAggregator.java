package proxy;

import utils.NodeConnectionsData;
import utils.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class DataAggregator {

    private List<Student> processedData;
    private List<Student> rawData;
    private static int nodesCounter = 0;
    private ProxyDataCollector dataCollector;

    public DataAggregator(ProxyDataCollector dataCollector) {
        rawData = new ArrayList<>();
        this.dataCollector = dataCollector;
    }

    public List<Student> process(String cmd) {

        rawData = dataCollector.collect();

        //data processing ...

        return processedData;
    }

    public static void setNodesCounter(int nodesCounter) {
        DataAggregator.nodesCounter = nodesCounter;
    }

    public static int getNodesCounter() {
        return nodesCounter;
    }
}