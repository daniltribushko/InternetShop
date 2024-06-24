package org.example.goods.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Класс исключений проекта
 */
@Getter
@Slf4j
public class AppException extends RuntimeException {
    private final String message;
    private final LocalDateTime timestamp;

    public AppException(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();

        log.error(message);
    }

}
