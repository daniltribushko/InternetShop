package org.example.userservice.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.example.userservice.models.dto.request.CreateTokenRequest;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 * <p>
 * Сервис для авторизации пользователец
 */
public interface AuthUserService {
    void sendCode(@Email
                  @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                  String email);
    String getToken(@Valid
                    CreateTokenRequest request);
}
