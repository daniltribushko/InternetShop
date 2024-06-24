package org.example.userservice.exceptions.authrequest;

import org.example.userservice.exceptions.GlobalAppException;
import org.example.userservice.models.AuthRequestStatus;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 */
public class AuthRequestByEmailAndStatusAndCodeNotFoundException extends GlobalAppException {
    public AuthRequestByEmailAndStatusAndCodeNotFoundException(String email, AuthRequestStatus status) {
        super(404, "AuthRequest with email: " + email + " and status: " + status + " not found");
    }
}
