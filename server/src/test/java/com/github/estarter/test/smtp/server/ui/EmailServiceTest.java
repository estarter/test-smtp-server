package com.github.estarter.test.smtp.server.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.estarter.test.smtp.server.smtp.MessageListener;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
public class EmailServiceTest {
    MessageListener listener;
    EmailService emailService;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        listener = new MessageListener();
        emailService = new EmailService(listener);

        listener.messages.add(new Email());
        listener.messages.add(new Email());
        listener.messages.add(new Email());
        for (int i = 0; i < listener.messages.size(); i++) {
            listener.messages.get(i).id = i+1;
            listener.messages.get(i).sender = "from1@test.com";
            listener.messages.get(i).recipients = new String[]{"to1@test.com"};
            listener.messages.get(i).subject = "subj " + i;
            listener.messages.get(i).body = "body " + i;
        }
        listener.messages.get(1).recipients = new String[]{"to1@test.com", "to2@test.com"};
        listener.messages.get(2).sender = "from2@test.com";
    }

    @Test
    public void listEmails() throws IOException {
        String output = emailService.listEmails();
        List result = mapper.readValue(output, List.class);
        assertEquals(Arrays.asList(1,2,3), result);
    }

    @Test
    public void listEmailsForUser() throws IOException {
        assertEquals(Arrays.asList(1,2),
                mapper.readValue(emailService.listEmailsForUser("from1@test.com"), List.class));
        assertEquals(Arrays.asList(3),
                mapper.readValue(emailService.listEmailsForUser("from2@test.com"), List.class));
    }

    @Test
    public void getEmail() throws IOException {
        String output = emailService.getEmail("2");
        Email result = mapper.readValue(output, Email.class);
        assertEquals(2, result.id);
        assertEquals("from1@test.com", result.sender);
        assertArrayEquals(new String[]{"to1@test.com", "to2@test.com"}, result.recipients);
        assertEquals("subj 1", result.subject);
        assertEquals("body 1", result.body);
    }

}
