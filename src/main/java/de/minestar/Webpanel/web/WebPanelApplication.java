package de.minestar.Webpanel.web;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * This is the core class for the web application
 */
public class WebPanelApplication extends ResourceConfig {

    public WebPanelApplication() {
        // Adding packages (and recursives) for the rest resources
        packages("de.minestar.Webpanel.web.resources");
    }

}
