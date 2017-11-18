import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Client {

    private DataOutputStream out;
    private BufferedReader in;
    private static String dataCmd = ".processData";

    public Client(String host, int port) {

        try {
            Socket clientSocket = new Socket(host, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //sentence = inFromUser.readLine();
            //outToServer.writeBytes(sentence + '\n');
            //modifiedSentence = inFromServer.readLine();
            //System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collectData() {

        try {
            out.writeBytes(dataCmd + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}