import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.minestar.Webpanel.web.WebService;

/**
 * Test to start the webservice with all its neccessary information
 */
public class StartWebServiceTest {

    // Write the standard output channels to a string
    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterClass
    public static void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void startNormal() throws IOException {
        WebService webservice = new WebService("http://localhost", 8321, ".");
        webservice.start();

        webservice.stop();
        // No warning - all templates found
        assertFalse(outContent.toString().contains("[ WARNING ] Template"));
    }

    @Test
    public void startWithWrongPath() throws IOException {

        WebService webservice = new WebService("http://localhost", 8321, "asd");
        webservice.start();

        webservice.stop();

        // There are warnings
        assertTrue(outContent.toString().contains("[ WARNING ] Template"));
    }
}
