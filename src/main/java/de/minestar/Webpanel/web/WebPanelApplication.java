package de.minestar.Webpanel.web;

import org.glassfish.jersey.server.ResourceConfig;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.services.AdminStuffService;
import de.minestar.Webpanel.services.MainPageService;
import de.minestar.Webpanel.services.ServerService;
import de.minestar.Webpanel.services.TemplateService;

/**
 * This is the core class for the web application
 */
public class WebPanelApplication extends ResourceConfig {

    public WebPanelApplication(String packageFolder) {
        AuthHandler.init();

        new TemplateService();
        new MainPageService();
        new ServerService();
        new AdminStuffService();

        // Adding packages (and recursives) for the rest resources
        packages(packageFolder);
    }

}
