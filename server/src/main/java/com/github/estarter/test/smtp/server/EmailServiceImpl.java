package com.github.estarter.test.smtp.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @author Alexey Merezhin
 */
public class EmailServiceImpl {

    @GET
    @Path("/emails")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return "ok";
    }
}
