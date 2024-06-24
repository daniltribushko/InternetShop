package org.example.userservice.exceptions.users;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 */
public class UserAlreadyExistException extends GlobalAppException {
    public UserAlreadyExistException(String email) {
        super(409, "User: " + email + " already exist");
    }
}
