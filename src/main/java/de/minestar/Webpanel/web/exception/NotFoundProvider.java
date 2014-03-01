package de.minestar.Webpanel.web.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.minestar.Webpanel.template.Template;

/**
 * Class handeling all 404 errors thrown by webserver
 * 
 * @param <HttpServletRequest>
 */
@Provider
public class NotFoundProvider implements ExceptionMapper<WebApplicationException> {

    public Response toResponse(NotFoundException exception) {
        System.out.println("error!");
        // System.out.println(exception.g);
        return Response.status(Response.Status.NOT_FOUND).entity(Template.get("error404").build()).build();
    }

    public Response toResponse(WebApplicationException exception) {
        String relativeFolder = "";
        if (exception.getMessage().equals("HTTP 404 Not Found")) {
            System.out.println("404");
        }
        return Response.status(Response.Status.NOT_FOUND).entity(Template.get("error404").setRelativeFolder(relativeFolder).build()).build();
    }
}