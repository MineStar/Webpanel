package de.minestar.Webpanel.web.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.web.security.LoginCookie;
import de.minestar.Webpanel.web.security.NewAuthHandler;

/**
 * This resource is for handeling the first page (normally the index.html)
 */
@Path("")
public class MainPageResource {

    @GET
    @Path("{a:index.html|login.html|}")
    @Produces("text/html")
    public Response getIndexHtml(@CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        // Has the user a cookie ? Show him the normal menu
        try {
            UserData userData = NewAuthHandler.check(cookie);
            Template template = Template.get("doLogin").set("USERNAME", userData.getUserName()).set("TOKEN", userData.getToken()).setUser(userData);
            return Response.ok(template.build()).build();
        } catch (Exception e) {
        }

        // Deliver the login page if not logged in or wrong cookie
        return Response.ok(Template.get("login").build()).build();
    }
}
