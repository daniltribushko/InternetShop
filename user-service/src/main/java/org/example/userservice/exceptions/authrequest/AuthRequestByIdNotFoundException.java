package org.example.userservice.exceptions.authrequest;

import org.example.userservice.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 */
public class AuthRequestByIdNotFoundException extends GlobalAppException {
    public AuthRequestByIdNotFoundException(Long id) {
        super(404, "AuthRequest with id " + id + " not found");
    }
}
