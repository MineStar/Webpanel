package de.minestar.Webpanel.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;

/**
 * This resource handels the doLogin.html
 * 
 */
@Path("")
public class LoginResource {

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
            return Response.ok(template.compile(user, rpl_user, rpl_token)).build();
        } else {
            return Response.status(Status.UNAUTHORIZED).entity(TemplateHandler.getTemplate("invalidLogin").compile(AuthHandler.defaultUser)).build();
        }
    }
}
