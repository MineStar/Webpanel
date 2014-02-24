package de.minestar.Webpanel.web.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.handler.TemplateHandler;

/**
 * Class handeling all 404 errors thrown by webserver
 */
@Provider
public class NotFoundProvider implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(TemplateHandler.getTemplate("error404").compile(AuthHandler.defaultUser)).build();
    }
}