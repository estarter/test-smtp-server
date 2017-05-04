package com.github.estarter.test.smtp.server;

import javax.jws.WebService;

/**
 * @author Alexey Merezhin
 */
@WebService
public interface EmailService {
    public Email[] getAll();

}
