package org.example.userservice.exceptions.roles;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 */
public class RoleAlreadyAddedException extends GlobalAppException {
    public RoleAlreadyAddedException(String email, String role) {
        super(409, "Role: " + role + " already added to user: " + email);
    }
}
