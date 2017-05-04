package com.github.estarter.test.smtp.server;

/**
 * @author Alexey Merezhin
 */
public class EmailServiceImpl implements EmailService {
    @Override
    public Email[] getAll() {
        Email email = new Email();
        return new Email[]{email};
    }
}
