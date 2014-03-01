package de.minestar.Webpanel.web.resources;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.glassfish.grizzly.http.util.MimeType;

import de.minestar.Webpanel.template.Template;

/**
 * This resource is for handeling the first page (normally the index.html)
 */
@Path("web/")
public class WebPageResource {

    // directory for the web files
    private static final File webFolder = new File("web");

    // The :.+ is for matchin also slashes
    @Path("{file:.+}")
    @GET
    public Response getContent(@Context UriInfo uriInfo, @PathParam("file") String file) {
        // Load file
        File f = new File(webFolder, file);

        // Throw 404 if not found
        if (!f.exists()) {
            // retrieve the relative folder
            int countSlashes = StringUtils.countMatches(uriInfo.getPath(), "/");
            String relativeFolder = "";
            for (int count = 1; count < countSlashes; count++) {
                relativeFolder += "../";
            }

            // build response
            return Response.status(Status.NOT_FOUND).entity(Template.get("error404").setRelativeFolder(relativeFolder).build().replaceAll("web/", "")).build();
        }

        // Return the file itself
        return Response.ok(f, MimeType.getByFilename(file)).build();
    }

}
