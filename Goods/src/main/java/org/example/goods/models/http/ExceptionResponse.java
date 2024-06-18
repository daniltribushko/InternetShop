package org.example.goods.models.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Dto исключения
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    @JsonProperty(value = "status_code")
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
}
