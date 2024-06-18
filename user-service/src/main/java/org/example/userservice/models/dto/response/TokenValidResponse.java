package org.example.userservice.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Dto валидности токена
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidResponse {
    private String email;
}
