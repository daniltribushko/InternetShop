package org.example.goods.aspect;

import org.example.goods.exceptions.GlobalAppException;
import org.example.goods.models.http.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Класс для перехвата исключений в контроллерах
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(GlobalAppException.class)
    public ResponseEntity<ExceptionResponse> handleGlobalAppException(GlobalAppException e) {
        int statusCode = e.getStatusCode();
        return ResponseEntity.status(statusCode)
                .body(new ExceptionResponse(statusCode, e.getMessage(), e.getTimestamp()));
    }
}
