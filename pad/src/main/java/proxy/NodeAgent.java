package proxy;

import utils.NodeConnectionsData;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by c-denipost on 24-Nov-17.
 *
 * Using UDP multi-cast collects statistics about Nodes' connections (# of nodea at the moment of client connection),
 * address and port on which they accept connections.
 **/
public class NodeAgent extends Thread {

    private Properties mProperties;
    private Properties uProperties;
    private MulticastSocket mSocket;
    private InetAddress group;
    private int nodesCounter = 0;

    //private static List<NodeConnectionsData> responses;

    public NodeAgent(ResponseProcessor rProcessor) {

        mProperties = Proxy.getMulticastProperties();
        uProperties = Proxy.getUnicastProperties();

        try {

            mSocket = new MulticastSocket(Integer.parseInt(mProperties.getProperty("port")));
            group = InetAddress.getByName(mProperties.getProperty("group"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rProcessor.start();
        sendCommand("ping");
        sendCommand(mProperties.getProperty("cmd") + ":" + uProperties.getProperty("host") + ":" +
                              uProperties.getProperty("port"));

        mSocket.close();
    }

    public void sendCommand(String command) {

        byte[] cmd;

        try {
            cmd = command.getBytes();

            DatagramPacket packet = new DatagramPacket(cmd, cmd.length, group, Integer.parseInt(mProperties.getProperty("port")));
            mSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
