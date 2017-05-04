package com.github.estarter.test.smtp.server;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.api.Properties;
import org.ops4j.pax.cdi.api.Property;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@OsgiServiceProvider(classes = Servlet.class)
@Properties({
    @Property(name = "osgi.http.whiteboard.servlet.pattern", value = "/emails") // For felix http, also in blueprint.xml
})
public class SmtpServerImpl extends HttpServlet {
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
        server.setPort(25);
        server.start();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String taskId = req.getParameter("emailId");
        PrintWriter writer = resp.getWriter();
        if (taskId != null && taskId.length() > 0) {
            showEmail(writer, taskId);
        } else {
            showEmailList(writer);
        }
    }

    private void showEmailList(PrintWriter writer) {
        writer.println("total " + messageListener.messages.size());
    }

    private void showEmail(PrintWriter writer, String taskId) {
        writer.println("yyy");
    }

}