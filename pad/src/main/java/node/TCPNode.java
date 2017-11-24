package node;

import java.net.Socket;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class TCPNode extends Thread{

    Socket nodeSocket;

    public TCPNode(Socket s) {
        this.nodeSocket = s;
    }


    /**
     * proxy:
     * node:
     */
    @Override
    public void run() {



    }
}
