package de.minestar.Webpanel.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;

/**
 * This resource is for handeling the first page (normally the index.html)
 */
@Path("")
public class MainPageResource {

    @GET
    @Path("{a:index.html|login.html|}")
    @Produces("text/html")
    public String getIndexHtml() {
        // Deliver the login page
        return TemplateHandler.getTemplate("login").compile(AuthHandler.defaultUser);
    }
}
