package org.example.userservice.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.userservice.models.entities.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Dto пользователя
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @Schema(description = "User's id",
            name = "id",
            type = "int64",
            example = "1")
    private Long id;
    @Schema(description = "User's email",
            name = "email",
            type = "string",
            example = "ivanov.ivan@gmail.com")
    private String email;
    @Schema(description = "User's creation date",
            name = "creationDate",
            type = "string",
            format = "date-time")
    private LocalDateTime creationDate;
    @Schema(description = "User's update date",
            name = "updateDate",
            type = "string",
            format = "date-time")
    private LocalDateTime updateDate;
    @Schema(description = "User's roles",
            name = "roles")
    private List<RoleResponse> roles;
}
