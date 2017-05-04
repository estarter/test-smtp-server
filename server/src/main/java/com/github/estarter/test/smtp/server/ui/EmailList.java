package com.github.estarter.test.smtp.server.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
@Path("emails")
public class EmailList {
    @GET
    public String action() {
        return "test";
    }
}
