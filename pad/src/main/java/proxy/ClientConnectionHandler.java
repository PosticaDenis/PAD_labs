package proxy;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by c-denipost on 24-Nov-17.
 *
 * Accepts client connections
 **/
public class ClientConnectionHandler extends Thread {

    private int port;
    public ClientConnectionHandler(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while(true) {
                ClientConnection clientConnection = new ClientConnection(serverSocket.accept());
                clientConnection.start();

                System.out.println("A client has connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
