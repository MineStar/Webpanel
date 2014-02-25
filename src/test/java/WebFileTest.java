import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.minestar.Webpanel.core.WebpanelSettings;
import de.minestar.Webpanel.web.WebPanelApplication;

/**
 * This tests the webserver delivering content from the web files
 */
public class WebFileTest extends JerseyTest {

    @Override
    protected Application configure() {
        WebpanelSettings settings = new WebpanelSettings(".");
        return new WebPanelApplication(settings.getPackageFolder());
    }

    @Test
    public void loadImageFile() {
        File imageFile = target("web").path("images/gui/btn_refresh.gif").request().get(File.class);
        assertNotNull(imageFile);
        assertEquals(221L, imageFile.length());
    }

    @Test(expected = NotFoundException.class)
    public void loadNotExistingImage() {
        // Access a resource not existing - expect 404
        target("web").path("images/gui/btn_refresh.gi").request().get(File.class);
    }

    @Test
    public void loadCssFile() {
        String cssContent = target("web").path("styles.css").request().get(String.class);
        assertNotNull(cssContent);
        assertFalse(cssContent.isEmpty());
    }

    @Test
    public void loadJqueryFile() {
        String jqueryContent = target("web").path("scripts/jquery/jquery-1.10.2.js").request().get(String.class);
        assertNotNull(jqueryContent);
        assertFalse(jqueryContent.isEmpty());
    }
}
