package proxy;

import java.io.IOException;
import java.net.*;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Proxy {

    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private static boolean notConnected = true;

    private static int multicastPort = 5000;
    private static String multicastGroup = "228.5.6.7";
    private static int ttl = 1;

    private static String unicastHost = "localhost";
    private static String unicastPort = "7777";

    public Proxy(int port) {

        try {
            //String clientSentence;
            //String capitalizedSentence;
            serverSocket = new ServerSocket(port);

            while (notConnected) {
                connectionSocket = serverSocket.accept();
                System.out.println("A client has connected.");
                notConnected = false;
                //BufferedReader inFromClient =
                //        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                //clientSentence = inFromClient.readLine();
                //System.out.println("Received: " + clientSentence);
                //capitalizedSentence = clientSentence.toUpperCase() + '\n';
                //outToClient.writeBytes(capitalizedSentence);
            }

            signalNodes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signalNodes() {

        String msg = unicastHost + unicastPort;

        try {
            InetAddress group = InetAddress.getByName(multicastGroup);

            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);

            DatagramPacket communicate = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
            multicastSocket.send(communicate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}