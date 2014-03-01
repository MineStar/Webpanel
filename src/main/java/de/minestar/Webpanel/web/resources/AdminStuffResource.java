package de.minestar.Webpanel.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.bukkit.Bukkit;

import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.MappedJSON;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.Webpanel.web.security.LoginCookie;
import de.minestar.Webpanel.web.security.NewAuthHandler;

/**
 * The resource for the Plugin AdminStuff
 */
@Path("AdminStuff")
public class AdminStuffResource extends MainPageResource {

    private static final String PATH_KICK = "kickPlayer";
    private static final String PATH_BAN = "banPlayer";

    /**
     * Main method - show the menu
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response index(@Context UriInfo uriInfo, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        UserData user = NewAuthHandler.check(uriInfo, cookie, UserLevel.MOD);

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

    // /////////////////////////////////////////////////////////////////////
    //
    // KICK PLAYER
    //
    // /////////////////////////////////////////////////////////////////////

    @POST
    @Path(PATH_KICK)
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postKickPlayer(@Context UriInfo uriInfo, MappedJSON json, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        NewAuthHandler.check(uriInfo, cookie, UserLevel.MOD);
        System.out.println("payload: " + json.getValue("playerName") + " - " + json.getValue("reason"));
        // Handle kick
        String testResponse = "{'status':200, 'playerName':'testkickuser'}";
        testResponse = testResponse.replaceAll("'", "\"");
        return Response.ok(testResponse).build();
    }

    @GET
    @Path(PATH_KICK)
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getKickPlayer(@Context UriInfo uriInfo, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        NewAuthHandler.check(uriInfo, cookie, UserLevel.MOD);
        return Response.ok(Template.get("error404").setRelativeFolder("../../").build()).build();
    }

    // /////////////////////////////////////////////////////////////////////
    //
    // BAN PLAYER
    //
    // /////////////////////////////////////////////////////////////////////

    @Path(PATH_BAN)
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response clickBanPlayer(@Context UriInfo uriInfo, @FormParam("player") String userToKick, @FormParam("reason") String reason, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        // Handle ban
        return index(uriInfo, cookie);
    }
}
