package de.minestar.Webpanel.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.units.UserData;

public class ForbiddenException extends WebApplicationException {

    private static final long serialVersionUID = -4493970301322309781L;

    public ForbiddenException() {
        this(AuthHandler.defaultUser);
    }

    public ForbiddenException(UserData user) {
        super(Response.status(Status.FORBIDDEN).entity(TemplateHandler.getTemplate("insufficentRights").compile(user)).build());
    }
}