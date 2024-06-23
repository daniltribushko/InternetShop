package org.example.goods.exceptions.users;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 */
public class UserNotBrandException extends GlobalAppException {
    public UserNotBrandException(String email) {
        super(403, "User: " + email + " not brand");
    }
}
