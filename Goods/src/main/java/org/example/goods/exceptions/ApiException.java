package org.example.goods.exceptions;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 *
 * Класс исключения api запроса
 */
public class ApiException extends GlobalAppException {
    public ApiException(int statusCode, String name) {
        super(statusCode, name);
    }
}
