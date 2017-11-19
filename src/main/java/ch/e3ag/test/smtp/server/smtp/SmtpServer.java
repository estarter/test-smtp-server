package ch.e3ag.test.smtp.server.smtp;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

public class SmtpServer {
    private static Logger logger = LoggerFactory.getLogger(SmtpServer.class);
    @Autowired
    private MessageListener listener;
    private SMTPServer server;
    private int port;

    public SmtpServer(int port) {
        File mailbox = new File(MessageListener.MAILBOX);
        if (!mailbox.exists()) {
            mailbox.mkdirs();
        }
        this.port = port;
        logger.info("SmtpServer constructor with port {}", port);
    }

    public void start() {
        if (server == null) {
            server = new SMTPServer(new SimpleMessageListenerAdapter(listener));
        }
        server.setHostName("localhost");
        server.setPort(port);

        logger.info("Starting smtp server");
        server.start();
    }

    public void stop() {
        logger.info("Stopping smtp server");
        server.stop();
    }
}