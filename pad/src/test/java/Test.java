import client.Client;
import node.Node;
import prxy.Proxy;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Test {

    @org.junit.Test
    public void testData() {

        Proxy proxy = new Proxy(1234);
        Client client = new Client("localhost", 1234);
        Node node = new Node();

        //client.collectData();
    }
}
