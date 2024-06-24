package org.example.userservice.exceptions.roles;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 */
public class RoleByNameNotFoundException extends GlobalAppException {
    public RoleByNameNotFoundException(String name) {
        super(404, "Role: " + name + " not found");
    }
}
