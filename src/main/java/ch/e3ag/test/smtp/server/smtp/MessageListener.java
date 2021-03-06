package ch.e3ag.test.smtp.server.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.TooMuchDataException;
import org.subethamail.smtp.helper.SimpleMessageListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;

import ch.e3ag.test.smtp.server.ui.Email;

/**
 * @author Alexey Merezhin
 */
@Component
public class MessageListener implements SimpleMessageListener {
    public static final String MAILBOX = "/opt/mailbox";
    static Logger logger = LoggerFactory.getLogger(MessageListener.class);
    public ArrayList<Email> messages = new ArrayList<>();
    private static int counter = 0;

    public boolean accept(String from, String recipient) {
        logger.info("accept message from {} to {}", from, recipient);
        return true;
    }

    public void deliver(String from, String recipient, InputStream data) throws TooMuchDataException, IOException {
        logger.info("deliver message from {} to {}", from, recipient);

        BufferedInputStream bufferedData = new BufferedInputStream(data);
        try {
            MimeMessage message = new MimeMessage(null, bufferedData);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            message.writeTo(baos);
            logger.debug(baos.toString());
            // message.writeTo(new FileOutputStream(getMessageFile(recipient, message)));
            logger.info("read email to {} subject '{}'", message.getRecipients(Message.RecipientType.TO), message.getSubject());
            messages.add(new Email(++counter, recipient, message));
        } catch (MessagingException e) {
            /* as it normally happens don't treat it as an error. todo how to fix? */
            logger.info("failed to read email");
        }
    }

    public File getMessageFile(String recipient, MimeMessage message) throws MessagingException {
        String part1 = recipient.substring(0, recipient.indexOf('@')).replaceAll("[\\W]+", "_");
        String part2 = "empty";
        if (message.getSubject() != null) {
            part2 = message.getSubject().replaceAll("[\\W]+", "_");
        }
        return new File(MAILBOX, part1 + "___" + part2 + ".msg");
    }

    public Email getEmail(int id) {
        for (Email msg : messages) {
            if (msg.id == id) return msg;
        }
        return null;
    }
}
