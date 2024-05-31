package org.example.userservice.exceptions.roles;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 */
public class UserDontHaveRoleException extends GlobalAppException {
    public UserDontHaveRoleException(String email, String role) {
        super(409, "User: " + email + " don't have role: " + role);
    }
}
