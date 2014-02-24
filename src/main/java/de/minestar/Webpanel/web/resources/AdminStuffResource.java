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
import org.bukkit.entity.Player;

import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.Webpanel.utils.Helper;
import de.minestar.Webpanel.web.LoginCookie;
import de.minestar.Webpanel.web.security.NewAuthHandler;

@Path("AdminStuff.html")
public class AdminStuffResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response clickAdminStuff(@CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        UserData user = NewAuthHandler.check(cookie, UserLevel.MOD);

        TemplateReplacement rpl_playerList = new TemplateReplacement("PLAYERLIST");
        TemplateReplacement rpl_user = new TemplateReplacement("USERNAME", user.getUserName());
        TemplateReplacement rpl_token = new TemplateReplacement("TOKEN", user.getToken());
        if (Bukkit.getServer() != null) {
            rpl_playerList.setValue(Helper.StringToDropDown("player", this.getPlayerList(), ";", "dropdown_big"));
        } else {
            rpl_playerList.setValue("Server ist offline!");
            rpl_playerList.setValue("GeMoschen!");
        }
        return Response.ok(TemplateHandler.getTemplate("AdminStuff").compile(user, rpl_playerList, rpl_user, rpl_token)).build();
    }

    private String getPlayerList() {
        // build response
        String response = "";
        Player[] list = Bukkit.getOnlinePlayers();
        for (Player player : list) {
            response += player.getName() + ";";
        }
        return response;
    }

    @Path("AS_kickPlayer")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response clickKickPlayer(@FormParam("player") String userToKick, @FormParam("reason") String reason, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {

        // Handle kick
        return clickAdminStuff(cookie);
    }

    @Path("AS_banPlayer")
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
        return clickAdminStuff(cookie);
    }
}
