package prxy;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class DataAggregator {

    private static String command;

    public DataAggregator() {
    }

    public void collectData(String maven) {

        processData();
    }

    private void processData() {

        sendDataToClient();
    }

    private void sendDataToClient() {

    }

    public static void setCommand(String command) {
        DataAggregator.command = command;
    }
}
