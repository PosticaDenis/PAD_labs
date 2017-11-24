package node;

import java.net.DatagramSocket;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class ConnectionHandler extends Thread{

    DatagramSocket nodeSocket;

    public ConnectionHandler(DatagramSocket nodeSocket) {
        this.nodeSocket = nodeSocket;
    }

    @Override
    public void run() {


    }
}
