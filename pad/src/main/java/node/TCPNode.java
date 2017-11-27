package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class TCPNode extends Thread{

    private String type;
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

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));

            String received;
            try {
                while (true) {

                    received = in.readLine();
                    if (received != null) {

                        if (received.contains("identify:")) {
                            type = received.split(":")[1];
                        }
                        if (received.contains("proxy:data")) {
                            sendData();
                        }
                        else {
                            collectAndSendData();
                        }
                    }
                }
            } catch (IOException e) {
                //removeThread();
            }

            in.close();
            nodeSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void sendData() {

    }

    private void collectAndSendData() {

    }
}
