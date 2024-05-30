package org.example.userservice.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Tribushko Dnil
 * @since 29.05.2024
 * <p>
 * Dto ответа исключения
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    @Schema(description = "Exception's http code",
            name = "status_code",
            example = "400",
            type = "integer")
    @JsonProperty(value = "status_code")
    private int statusCode;
    @Schema(description = "Exception's message",
            name = "message",
            example = "BAD REQUEST",
            type = "string")
    private String message;
    @Schema(description = "Exception's timestamp",
            name = "timestamp",
            type = "string",
            format = "date-time")
    private LocalDateTime timestamp;
}
