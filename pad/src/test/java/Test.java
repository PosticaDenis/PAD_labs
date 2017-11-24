import client.Client;
import proxy.Proxy;

/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Test {

    @org.junit.Test
    public void testData() {

        Proxy proxy = new Proxy(4321);
        Client client = new Client("localhost", 1234);

        client.collectData();
    }
}
