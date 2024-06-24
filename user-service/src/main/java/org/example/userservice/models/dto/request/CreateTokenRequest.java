package org.example.userservice.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRequest {
    @Schema(description = "User code received from the email",
            name = "code",
            type = "string",
            example = "0110")
    @NotBlank(message = "Code can not be blank")
    private String code;
    @Schema(description = "User email",
            name = "email",
            type = "string",
            example = "ivanov.ivan@gmail.com")
    @Email
    @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
    private String email;
}
