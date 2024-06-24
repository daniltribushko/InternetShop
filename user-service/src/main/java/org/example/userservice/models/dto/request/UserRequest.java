package org.example.userservice.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Dto запроса на работу с пользователем
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Schema(description = "User's email",
            name = "user",
            type = "string",
            example = "ivanov.ivan@gmail.com")
    @Email
    @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
    private String email;
}
