package node;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

        System.out.println("Started node TCP");
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));

            String received;

            try {
                while (true) {

                    //System.out.println("1...");
                    received = in.readLine();

                    System.out.println("Received: " + received);

                    if (received != null) {

                        System.out.println("Received a message through TCP");
                        if (received.contains("identify:")) {
                            type = received.split(":")[1];

                            System.out.println("A proxy has connected.");
                        }
                        if (received.contains("proxy:data")) {
                            System.out.println("Proxy required data.");
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

        try {
            DataOutputStream out = new DataOutputStream(nodeSocket.getOutputStream());
            out.writeBytes("A lot of data\r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectAndSendData() {

    }
}
