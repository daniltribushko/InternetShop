package org.example.userservice.aspects;

import org.example.userservice.exceptions.GlobalAppException;
import org.example.userservice.models.dto.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Класс для перехвата исключений
 */
@RestControllerAdvice
public class GlobalAppExceptionHandler {
    @ExceptionHandler(GlobalAppException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(GlobalAppException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ExceptionResponse(e.getStatusCode(),
                        e.getLocalizedMessage(),
                        e.getTimestamp())
                );
    }
}
