package proxy;

import java.io.IOException;
import java.net.*;
import java.util.Properties;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Proxy {

    private static Properties unicastProperties;
    private static Properties multicastProperties;
    private static Properties cProperties;

    private ClientConnectionHandler clientConnectionHandler;
    //private static DataAggregator dataAggregator;
    //private NodeAgent nodeAgent;

    public Proxy(int port) {


        unicastProperties = new Properties();
        multicastProperties = new Properties();
        cProperties = new Properties();

        //dataAggregator = new DataAggregator();
        clientConnectionHandler = new ClientConnectionHandler(port);
        //nodeAgent = new NodeAgent();

        try {
            unicastProperties.load(Proxy.class.getResourceAsStream("/proxy/unicast.properties"));
            multicastProperties.load(Proxy.class.getResourceAsStream("/proxy/multicast.properties"));
            cProperties.load(Proxy.class.getResourceAsStream("/proxy/cmds.properties"));

            clientConnectionHandler.start();
            //dataAggregator.start();
            //nodeAgent.start();
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("One or more properties file was not found!");
        }
    }

    public static Properties getUnicastProperties() {
        return unicastProperties;
    }

    public static Properties getMulticastProperties() {
        return multicastProperties;
    }

    public static Properties getcProperties() {
        return cProperties;
    }
}