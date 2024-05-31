package org.example.userservice.exceptions.users;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 */
public class UserNotAdminException extends GlobalAppException {
    public UserNotAdminException(String email) {
        super(403, "User: " + email + " not admin");
    }
}
