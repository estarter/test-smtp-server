package ch.e3ag.test.smtp.server.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.e3ag.test.smtp.server.smtp.MessageListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexey Merezhin
 */
@RestController
public class RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);
    @Autowired
    private MessageListener listener;

    @RequestMapping("/emails")
    public List<Email> listEmails() {
        logger.info("return all emails");
        return listener.messages;
    }

    @RequestMapping("/user/{from}/emails")
    public List<Email> listEmailsForUser(@PathVariable String from) {
        List<Email> messages = listener.messages.stream()
                                            .filter(msg -> msg.user != null && msg.user.equals(from))
                                            .collect(Collectors.toList());
        logger.info("user {} all emails {}", from, messages);
        return messages;
    }

    @RequestMapping("/emails/{emailId}")
    public Email getEmail(@PathVariable String emailId) {
        logger.info("emailId {}", emailId);

        int id = Integer.parseInt(emailId);
        Email msg = listener.getEmail(id);
        logger.info("emails {}", msg);
        return msg;
    }

    @RequestMapping("/delete")
    public int deleteEmails() {
        int originalAmount = listener.messages.size();
        listener.messages.clear();
        logger.info("delete all emails");
        return originalAmount - listener.messages.size();
    }

    @RequestMapping("/delete/{emailId}")
    public int deleteEmails(@PathVariable String emailId) {
        int originalAmount = listener.messages.size();
        int id = Integer.parseInt(emailId);
        Email msg = listener.getEmail(id);
        listener.messages.remove(msg);
        logger.info("delete emails {} ", msg);
        return originalAmount - listener.messages.size();
    }
}
