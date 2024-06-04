package org.example.goods.exceptions;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 */
public class TokenNotValidException extends GlobalAppException{
    public TokenNotValidException() {
        super(400, "Token not valid");
    }
}
