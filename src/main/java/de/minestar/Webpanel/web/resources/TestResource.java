package de.minestar.Webpanel.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * This is just a normal test resource and has no other use in production
 */
@Path("test")
public class TestResource {

    // Test Resource just returning Hello World
    @Path("helloWorld")
    @GET
    public String helloWorld() {
        return "Hello World";
    }

    // Test Resource saying hello to the name given by the URL
    @Path("hello/{name}")
    @GET
    public String helloName(@PathParam("name") String name) {
        return "Hello " + name;
    }
}
