package proxy;

import utils.NodeConnectionsData;
import utils.Student;

import java.util.List;

/**
 * Created by c-denipost on 24-Nov-17.
 *
 * Collects all the data from the Nodes, without aggregating it.
 **/
public class ProxyDataCollector {

    private List<NodeConnectionsData> connectionsData;

    public ProxyDataCollector() {

    }

    public List<Student> collect() {

        identifyMaven();
        //connect to the maven using TCP and ask for all the data from the connected Nodes.

    }

    private void identifyMaven(List<NodeConnectionsData> data) {

    }

    private void
}
