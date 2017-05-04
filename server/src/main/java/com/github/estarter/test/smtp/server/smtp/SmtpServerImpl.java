package com.github.estarter.test.smtp.server.smtp;

import java.io.File;

import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

public class SmtpServerImpl  {
    private final MessageListener messageListener;
    private final SMTPServer server;

    public SmtpServerImpl(MessageListener messageListener) {
        this.messageListener = messageListener;

        File mailbox = new File(MessageListener.MAILBOX);
        if (!mailbox.exists()) {
            mailbox.mkdirs();
        }

        server = new SMTPServer(new SimpleMessageListenerAdapter(messageListener));
        server.setHostName("localhost");
        server.setPort(25);
        server.start();
    }
}