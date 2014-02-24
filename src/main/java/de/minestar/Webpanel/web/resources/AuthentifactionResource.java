package de.minestar.Webpanel.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.web.LoginCookie;
import de.minestar.Webpanel.web.exception.UnauthorizedException;

/**
 * This resource handels the authentification of user (loging and logout)
 * 
 */
@Path("")
public class AuthentifactionResource {

    /**
     * Handeling the login
     */
    @Path("doLogin.html")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("txt_username") String userName, @FormParam("txt_password") String password) {
        if (userName != null && password != null && AuthHandler.doLogin(userName, password)) {
            // get userdata
            UserData user = AuthHandler.getUser(userName);

            Template template = TemplateHandler.getTemplate("doLogin");
            TemplateReplacement rpl_user = new TemplateReplacement("USERNAME");
            TemplateReplacement rpl_token = new TemplateReplacement("TOKEN");

            // update replacements...
            rpl_user.setValue(userName);
            rpl_token.setValue(user.getToken());
            String token = user.getToken();

            return Response.ok(template.compile(user, rpl_user, rpl_token)).cookie(LoginCookie.createNew(userName, token)).build();
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Handling the logout
     */
    @Path("logout.html")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response logout(@CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        return Response.ok(TemplateHandler.getTemplate("logout").compile(AuthHandler.defaultUser)).cookie(LoginCookie.delete(cookie)).build();
    }
}
