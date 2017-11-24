package utils;

import java.io.Serializable;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class NodeConnectionsData implements Serializable{

    private int counter = 0;
    private String tcpHost;
    private String tcpPort;

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setTcpHost(String tcpHost) {
        this.tcpHost = tcpHost;
    }

    public void setTcpPort(String tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getCounter() {
        return counter;
    }

    public String getTcpHost() {
        return tcpHost;
    }

    public String getTcpPort() {
        return tcpPort;
    }
}
