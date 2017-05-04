package com.github.estarter.test.smtp.server.ui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
public class EmailTest {

    @Test
    public void testJson() throws IOException, MessagingException {
        Properties props = new Properties();
        MimeMessage msg = new MimeMessage(Session.getDefaultInstance(props));
        msg.setFrom(new InternetAddress("sender@test.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rcp@test.com"));
        msg.setSubject("test message");
        msg.setText("msg body");

        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        String jsonString = mapper.writeValueAsString(new Email(1, msg));
        assertEquals("{\"id\":1,\"sender\":\"sender@test.com\",\"recipients\":[\"rcp@test.com\"],\"subject\":\"test message\",\"body\":\"msg body\"}", jsonString);
    }

}