package org.example.userservice.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Dto на получение всех пользователей
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllUserResponse {
    @Schema(description = "List of users",
            name = "users")
    private List<UserResponse> users;
}
