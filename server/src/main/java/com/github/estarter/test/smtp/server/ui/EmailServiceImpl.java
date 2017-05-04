package com.github.estarter.test.smtp.server.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.estarter.test.smtp.server.smtp.MessageListener;

/**
 * @author Alexey Merezhin
 */
public class EmailServiceImpl {
    private MessageListener listener;

    public EmailServiceImpl(MessageListener listener) {
        this.listener = listener;
    }

    @GET
    @Path("/emails")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return "ok " + listener.messages.size();
    }
}
