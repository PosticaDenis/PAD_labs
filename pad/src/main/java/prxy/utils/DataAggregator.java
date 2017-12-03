package prxy.utils;

/**
 * Created by c-denipost on 27-Nov-17.
 **/
public class DataAggregator extends Thread {

    private static String command = "No command";
    private static String data = "No data";

    @Override
    public void run() {

        System.out.println("Started Data Aggregator");
        while (true) {
            if (data.equals("No data")) {
                System.out.println("Processing data from MAVEN..." + data + "with command" + command);
                break;
            }
        }
    }

    public static void setCommand(String command) {
        System.out.println("Updated aggregator with command: " + command);
        DataAggregator.command = command;
    }

    public static void setData(String data) {

        System.out.println("Updated aggregator with data: " + data);
        DataAggregator.data = data;
    }
}
