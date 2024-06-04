package org.example.goods.exceptions.users;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 */
public class UserNotAdminException extends GlobalAppException {
    public UserNotAdminException(String email) {
        super(403, "User: " + email + " not admin");
    }
}
