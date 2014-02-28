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

import org.bukkit.Bukkit;

import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.Webpanel.web.security.LoginCookie;
import de.minestar.Webpanel.web.security.NewAuthHandler;

/**
 * The resource for the Plugin AdminStuff
 */
@Path("AdminStuff")
public class AdminStuffResource {

    /**
     * Main method - show the menu
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response clickAdminStuff(@CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        UserData user = NewAuthHandler.check(cookie, UserLevel.MOD);

        // TemplateReplacement rpl_playerList = new
        // TemplateReplacement("PLAYERLIST");

        if (Bukkit.getServer() != null) {
            // rpl_playerList.setValue(Helper.StringToDropDown("player",
            // this.getPlayerList(), ";", "dropdown_big"));
        } else {
            // rpl_playerList.setValue("Server ist offline!");
            // rpl_playerList.setValue("GeMoschen!");
        }

        Template template = Template.get("AdminStuff"); // .set(rpl_playerList.getName(),
                                                        // rpl_playerList.getValue());
        template = template.setUser(user);
        return Response.ok(template.build()).build();
    }

    @POST
    @Path("kickPlayer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response clickKickPlayer(String json, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        UserData user = NewAuthHandler.check(cookie, UserLevel.MOD);
        System.out.println("payload: " + json);
        // Handle kick
        String testResponse = "{'status':200, 'playerName':'testkickuser'}";
        testResponse = testResponse.replaceAll("'", "\"");
        return Response.ok(testResponse).build();
    }

    @Path("banPlayer")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response clickBanPlayer(@FormParam("player") String userToKick, @FormParam("reason") String reason, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        // Handle ban
        return clickAdminStuff(cookie);
    }

    @Path("AS_godmode")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response clickGodPlayer(@FormParam("player") String userToKick, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        // Handle godmode
        return Response.ok("okay!").build();
    }
}
