package org.example.userservice.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 * <p>
 * Абстрактный класс ошибок приложения
 */
@Slf4j
@Getter
public abstract class GlobalAppException extends RuntimeException {
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp;

    protected GlobalAppException(final int statusCode, final String message) {
        timestamp = LocalDateTime.now();

        this.statusCode = statusCode;
        this.message = message;

        String errorMessage = statusCode + " " + message;
        log.warn(errorMessage);
    }
}
