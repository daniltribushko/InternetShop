package org.example.userservice.exceptions.users;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 */
public class UserTryChangeAnotherUserException extends GlobalAppException {
    public UserTryChangeAnotherUserException(String currentEmail, String email) {
        super(403, "User: " + currentEmail + " trying to change user: " + email);
    }
}
