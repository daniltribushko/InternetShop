package org.example.userservice.exceptions.authrequest;

import org.example.userservice.exceptions.GlobalAppException;
import org.example.userservice.models.AuthRequestStatus;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 */
public class AuthRequestAlreadyExistException extends GlobalAppException {
    public AuthRequestAlreadyExistException(String email, AuthRequestStatus status) {
        super(409, "AuthRequest with email: " + email + " and status: " + status + " already exist");
    }
}
