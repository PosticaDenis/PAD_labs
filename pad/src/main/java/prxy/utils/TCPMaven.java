package prxy.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Dennis on 03-Dec-17.
 **/
public class TCPMaven extends Thread {

    private String mavenHost;
    private int mavenPort;

    private DataOutputStream out;
    private BufferedReader in;

    private Socket clientSocket;

    /*public static void main(String[] args) {
        TCPMaven tcpMaven = new TCPMaven("localhost", 9562);
        tcpMaven.start();
    }*/

    public TCPMaven(String mavenHost, int mavenPort) {

        this.mavenHost = mavenHost;
        this.mavenPort = mavenPort;

        try {

            System.out.println("Connect to Node TCP on " + mavenHost + ":" + mavenPort);
            this.clientSocket = new Socket(mavenHost, mavenPort);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //System.out.println("Proxy TCP identification");
            //Thread.sleep(5000);
            sendCommand("identify:proxy\r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("Proxy required data through TCP");
        sendCommand("proxy:data\r");

        String data;
        /*BufferedReader inFromServer = null;
        try {
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        while (true) {
            try {
                data = in.readLine();

                if (data != null) {

                    System.out.println("received DATA from Maven : " + data);
                    DataAggregator.setData(data);
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void sendCommand(String cmd) {
        System.out.println("proxy sent to maven " + cmd);
        try {
            Thread.sleep(500);
            //DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            out.writeBytes(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
