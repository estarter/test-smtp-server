package ch.e3ag.test.smtp.server.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
public class Email {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    public int id;
    /* it could be many recipients but user is to whom the email was delivered */
    public String user;
    public String sender;
    public String[] recipients;
    public String subject;
    public String body;

    public Email() {
    }

    public Email(int id, String user, MimeMessage msg) throws MessagingException {
        this.id = id;
        this.user = user;
        sender = getRecipients(msg.getFrom())[0];
        recipients = getRecipients(msg.getRecipients(Message.RecipientType.TO));
        subject = msg.getSubject();
        try {
            Object msgContent = msg.getContent();
            if (msgContent instanceof Multipart) {
                Multipart multipart = (Multipart) msgContent;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String disposition = bodyPart.getDisposition();
                    if (disposition != null &&
                            disposition.equalsIgnoreCase("ATTACHMENT")) {
                        logger.info("Message has attachment");
                    } else {
                        body = bodyPart.getContent().toString();
                    }
                }
                // body = String.valueOf(msg.getContent());
            } else {
                body = msgContent.toString();
            }
        } catch (IOException e) {
            body = "ERR";
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
