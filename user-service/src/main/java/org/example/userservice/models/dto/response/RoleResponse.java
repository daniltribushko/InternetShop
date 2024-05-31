package org.example.userservice.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.userservice.models.entities.Role;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Dto роли
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    @Schema(description = "Role's id",
            name = "id",
            type = "integer",
            example = "1")
    private Integer id;
    @Schema(description = "Role's name",
            name = "name",
            type = "string",
            example = "USER")
    private String name;

    public static RoleResponse mapFromEntity(Role role) {
        return new RoleResponse(role.getId(), role.getName());
    }
}
