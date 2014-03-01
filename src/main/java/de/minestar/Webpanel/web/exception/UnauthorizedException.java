package de.minestar.Webpanel.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.UserData;

public class UnauthorizedException extends WebApplicationException {

    private static final long serialVersionUID = -3612070483237198592L;

    public UnauthorizedException(UriInfo uriInfo) {
        this(uriInfo, AuthHandler.defaultUser);
    }

    public UnauthorizedException(UriInfo uriInfo, UserData user) {
        super(Response.status(Status.UNAUTHORIZED).entity(Template.get("invalidLogin").setRelativeFolder(uriInfo).setUser(user).build()).build());
    }

}
