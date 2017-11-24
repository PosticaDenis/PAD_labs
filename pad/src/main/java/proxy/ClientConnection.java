package proxy;

import proxy.DataAggregator;
import proxy.NodeAgent;
import proxy.Proxy;
import utils.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-denipost on 24-Nov-17.
 *
 * Reads client commands.
 **/
public class ClientConnection extends Thread{

    private Socket cSocket;
    private DataAggregator dataAggregator;
    private NodeAgent nodeAgent;
    private ProxyDataCollector proxyDataCollector;
    private ResponseProcessor responseProcessor;
    private List<Student> datas;


    public ClientConnection(Socket s) {

        cSocket = s;
        responseProcessor = new ResponseProcessor();
        nodeAgent = new NodeAgent(responseProcessor);
        proxyDataCollector = new ProxyDataCollector();
        dataAggregator = new DataAggregator(proxyDataCollector);

        datas = new ArrayList<>();
    }

    @Override
    public void run() {

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));

            String cmd;
            try {
                while (true) {

                    cmd = in.readLine();
                    if (cmd != null) {

                        //nodeAgent.start();
                        //Proxy.getDataAggregator();
                        datas = dataAggregator.process(cmd); //returns processed data according to client command
                        if (datas.size() > 0) {
                            //send processed data to client
                        }
                    }
                }
            } catch (IOException e) {
                //removeThread();
                e.printStackTrace();
            }

            //in.close();
            //cSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
