package de.minestar.Webpanel.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.UserData;

public class UnauthorizedException extends WebApplicationException {

    private static final long serialVersionUID = -3612070483237198592L;

    public UnauthorizedException() {
        this(AuthHandler.defaultUser);
    }

    public UnauthorizedException(UserData user) {
        super(Response.status(Status.UNAUTHORIZED).entity(Template.get("invalidLogin").setUser(user).build()).build());
    }
}
