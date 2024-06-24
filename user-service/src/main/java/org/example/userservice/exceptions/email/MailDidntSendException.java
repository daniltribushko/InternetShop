package org.example.userservice.exceptions.email;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 29.05.2024
 */
public class MailDidntSendException extends GlobalAppException {
    public MailDidntSendException(String email, String message) {
        super(400, "Mail didn't sent on email: " + email + ", " + message);
    }
}
