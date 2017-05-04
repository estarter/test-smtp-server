package com.github.estarter.test.smtp.client.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.estarter.test.smtp.server.ui.Email;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
public class Client {
    static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws MessagingException, IOException {
        deleteAll();
        assert getAllMessages().length == 0;
        sendMessage("from1@test.com", "to1@test.com", "test subj", "test body");
        assert getAllMessages().length == 1;
        sendMessage("from1@test.com", "to1@test.com", "test subj 2", "test body");
        Integer[] msgIds = getAllMessages();
        Email msg = getMessage(msgIds[msgIds.length-1]);
        assert msg.subject.equals("test subj 2");
    }

    private static void sendMessage(String from, String to, String subj, String body) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "localhost");
        props.setProperty("mail.smtp.port", "10025");
        MimeMessage msg = new MimeMessage(Session.getDefaultInstance(props));
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subj);
        msg.setText(subj);
        Transport.send(msg);
    }

    private static void deleteAll() throws IOException {
        URL url = new URL("http://localhost:8080/delete");
        try (InputStream is = url.openStream()) {}
    }

    private static Integer[] getAllMessages() throws IOException {
        URL url = new URL("http://localhost:8080/emails");
        try (InputStream is = url.openStream()) {
            return mapper.readValue(is, Integer[].class);
        }
    }

    private static Email getMessage(Integer msgId) throws IOException {
        URL url = new URL("http://localhost:8080/emails/"+msgId);
        try (InputStream is = url.openStream()) {
            return mapper.readValue(is, Email.class);
        }
    }

}
