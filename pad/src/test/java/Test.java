/**
 * Created by c-denipost on 18-Nov-17.
 **/
public class Test {

    @org.junit.Test
    public void testData() {

        Client client = new Client("localhost", 1234);

        client.collectData();
    }
}
