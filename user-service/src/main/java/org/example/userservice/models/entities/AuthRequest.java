package org.example.userservice.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.userservice.models.AuthRequestStatus;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 * <p>
 * Сущность запроса на авторизацию пользователя
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auth_requests")
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "code")
    private String code;
    @Column(nullable = false, name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private AuthRequestStatus status;
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;
    @Column(nullable = false, name = "update_date")
    private LocalDateTime updateDate;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String email;
        private AuthRequestStatus status;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder status(AuthRequestStatus status) {
            this.status = status;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public AuthRequest build() {
            AuthRequest authRequest = new AuthRequest();

            authRequest.id = this.id;
            authRequest.code = this.code;
            authRequest.email = this.email;
            authRequest.status = this.status;
            authRequest.creationDate = this.creationDate;
            authRequest.updateDate = this.updateDate;

            return authRequest;
        }
    }
}
