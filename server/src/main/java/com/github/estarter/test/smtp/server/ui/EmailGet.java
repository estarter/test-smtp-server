package com.github.estarter.test.smtp.server.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
@Path("emails/{id}")
public class EmailGet {

    @GET
    public String action(@PathParam("id") String emailId) {
        return "email id " + emailId;
    }

}
