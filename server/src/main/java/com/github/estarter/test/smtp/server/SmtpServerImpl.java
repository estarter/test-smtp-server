package com.github.estarter.test.smtp.server;

import java.io.File;
import java.io.PrintWriter;

import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

public class SmtpServerImpl {
    private final MessageListener messageListener;
    private final SMTPServer server;

    public SmtpServerImpl() {
        File mailbox = new File(MessageListener.MAILBOX);
        if (!mailbox.exists()) {
            mailbox.mkdirs();
        }

        messageListener = new MessageListener();
        server = new SMTPServer(new SimpleMessageListenerAdapter(messageListener));
        server.setHostName("localhost");
        server.setPort(10025);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop();
    }

    private void showEmailList(PrintWriter writer) {
        writer.println("total " + messageListener.messages.size());
    }

    private void showEmail(PrintWriter writer, String taskId) {
        writer.println("yyy");
    }

}