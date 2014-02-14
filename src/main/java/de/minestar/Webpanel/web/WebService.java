package de.minestar.Webpanel.web;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

import de.minestar.Webpanel.core.WebpanelSettings;

public class WebService {

    private HttpServer webServer;

    public WebService(String baseURI, int port, String folder) {
        new WebpanelSettings(folder);
        
        URI restURI = UriBuilder.fromUri(baseURI).port(port).build();
        ResourceConfig config = new WebPanelApplication();
        this.webServer = GrizzlyHttpServerFactory.createHttpServer(restURI, config);
    }

    public void start() throws IOException {
        this.webServer.start();
    }

    public void stop() {
        this.webServer.shutdown();
    }
}
