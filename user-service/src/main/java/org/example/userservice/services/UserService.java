package org.example.userservice.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.userservice.models.dto.request.UserRequest;
import org.example.userservice.models.dto.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Сервис для работы с пользователями
 */
public interface UserService {
    UserResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                          Long id);

    UserResponse update(@Min(value = 1, message = "Id can not be less than 1")
                        Long id,
                        @Email
                        @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                        String email,
                        @Valid
                        UserRequest userRequest);

    List<UserResponse> findAll(String role,
                               LocalDateTime creationDate,
                               LocalDateTime updateDate,
                               LocalDateTime minCreationDate,
                               LocalDateTime maxCreationDate,
                               LocalDateTime minUpdateDate,
                               LocalDateTime maxUpdateDate);

    void delete(@Min(value = 1, message = "Id can not be less than 1")
                Long id,
                @Email
                @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                String email);

    UserResponse addRole(@Min(value = 1, message = "Id can not be less than 1")
                         Long id,
                         @Email
                         @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                         String email,
                         @NotBlank(message = "Role can not be blank")
                         String role);

    UserResponse deleteRole(@Min(value = 1, message = "Id can not be less than 1")
                            Long id,
                            @Email
                            @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                            String email,
                            @NotBlank(message = "Role can not be blank")
                            String role);
}
