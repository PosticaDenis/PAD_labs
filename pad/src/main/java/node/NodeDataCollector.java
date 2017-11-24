package node;

import java.net.Socket;

/**
 * Created by c-denipost on 23-Nov-17.
 **/
public class NodeDataCollector extends Thread{

    private Socket connectionSocket;

    public NodeDataCollector(Socket socket) {
        connectionSocket = socket;
    }

    public void sendData() {

    }
}
