package prxy.utils;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class DataAggregator extends Thread {

    private static String command;
    private static String data;

    @Override
    public void run() {

        while (true) {
            if (data != null) {
                System.out.println("Processing data from MAVEN..." + data);
                break;
            }
        }
    }

    public static void setCommand(String command) {
        DataAggregator.command = command;
    }

    public static void setData(String data) {
        DataAggregator.data = data;
    }
}
