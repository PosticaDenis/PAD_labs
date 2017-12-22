package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Client {

    private DataOutputStream out;
    private BufferedReader in;
    private static String dataCmd = "get";

    public Client(String host, int port) {

        try {
            Socket clientSocket = new Socket(host, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Scanner input = new Scanner(System.in);

            while (true) {

                String cmd = input.next();

                if (cmd != null) {

                    if (cmd.equals(dataCmd)) {
                        sendCmd(dataCmd);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCmd(String cmd) {

        try {
            out.writeBytes(cmd + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("localhost", 1234);
    }
}