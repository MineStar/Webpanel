package de.minestar.Webpanel.web.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateReplacement;
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

            Template template = TemplateHandler.getTemplate("doLogin");
            TemplateReplacement rpl_user = new TemplateReplacement("USERNAME", userData.getUserName());
            TemplateReplacement rpl_token = new TemplateReplacement("TOKEN", userData.getToken());
            return Response.ok(template.compile(userData, rpl_user, rpl_token)).build();
        } catch (Exception e) {
        }

        // Deliver the login page if not logged in or wrong cookie
        return Response.ok(TemplateHandler.getTemplate("login").compile(AuthHandler.defaultUser)).build();

    }
}
