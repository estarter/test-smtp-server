package com.github.estarter.test.smtp.server.ui;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
public class Email {
    public int id;
    public String sender;
    public String[] recipients;
    public String subject;
    public String body;

    public Email() {
    }

    public Email(int id, MimeMessage msg) throws MessagingException {
        this.id = id;
        sender = getRecipients(msg.getFrom())[0];
        recipients = getRecipients(msg.getRecipients(Message.RecipientType.TO));
        subject = msg.getSubject();
        try {
            body = String.valueOf(msg.getContent());
        } catch (IOException e) {
        }
    }

    private String[] getRecipients(Address[] rcp) {
        String[] result = new String[rcp.length];
        for (int i = 0; i < rcp.length; i++) {
            if (rcp[i] instanceof InternetAddress) {
                result[i] = ((InternetAddress) rcp[i]).getAddress();
            } else {
                result[i] = String.valueOf(rcp[i]);
            }
        }
        return result;
    }
}
