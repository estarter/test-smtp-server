package com.github.estarter.test.smtp.server.ui;

import javax.mail.MessagingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.estarter.test.smtp.server.smtp.MessageListener;

/**
 * @author Alexey Merezhin
 */
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private MessageListener listener;
    private ObjectMapper mapper;

    public EmailService(MessageListener listener) {
        this.listener = listener;
        mapper = new ObjectMapper();
    }

    @GET
    @Path("/emails")
    @Produces(MediaType.APPLICATION_JSON)
    public String listEmails() throws JsonProcessingException {
        Object[] msgIds = listener.messages.stream()
                                            .map(msg -> msg.id)
                                            .toArray();
        logger.info("return all emails {}", msgIds);
        return mapper.writeValueAsString(msgIds);
    }

    @GET
    @Path("/user/{from}/emails")
    @Produces(MediaType.APPLICATION_JSON)
    public String listEmailsForUser(@PathParam("from") String from) throws JsonProcessingException {
        Object[] msgIds = listener.messages.stream()
                                           .filter(msg -> msg.sender.equals(from))
                                           .map(msg -> msg.id)
                                           .toArray();
        logger.info("user {} all emails {}", from, msgIds);
        return mapper.writeValueAsString(msgIds);
    }

    @GET
    @Path("/emails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmail(@PathParam("id") String emailId) throws JsonProcessingException {
        int id = Integer.parseInt(emailId);
        Email msg = listener.getEmail(id);
        logger.info("emails {}", msg);
        return mapper.writeValueAsString(msg);
    }

}
