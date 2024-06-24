package org.example.userservice.exceptions.users;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 */
public class UserByEmailNotFoundException extends GlobalAppException {
    public UserByEmailNotFoundException(String email) {
        super(404, "User: " + email + " not found");
    }
}
