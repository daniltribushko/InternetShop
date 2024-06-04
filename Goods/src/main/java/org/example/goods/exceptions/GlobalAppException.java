package org.example.goods.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public abstract class GlobalAppException extends RuntimeException {
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp;

    protected GlobalAppException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        String errorMessage = String.format("%s: %s",statusCode, message);
        log.warn(errorMessage);
    }
}
