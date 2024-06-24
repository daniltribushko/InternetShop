package org.example.userservice.exceptions.token;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 */
public class TokenNotValidException extends GlobalAppException {
    public TokenNotValidException() {
        super(400, "Token not valid");
    }
}
