package com.github.estarter.test.smtp.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Alexey Merezhin
 */
@Path("/")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface Server {

    @Path("/execute/{emailId}/")
    String execute(@PathParam("emailId") Integer emailId);
}
