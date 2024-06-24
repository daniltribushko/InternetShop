package org.example.userservice.exceptions.users;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 */
public class UserByIdNotFoundException extends GlobalAppException {
    public UserByIdNotFoundException(Long id) {
        super(404, "User with id " + id + " not found");
    }
}
