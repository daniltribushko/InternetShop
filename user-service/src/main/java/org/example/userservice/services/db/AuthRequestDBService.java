package org.example.userservice.services.db;

import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Сервис для работы с запросами на авторизацию в бд
 */
public interface AuthRequestDBService extends DBService<AuthRequest, Long> {
    AuthRequest findByEmailAndStatusAndCode(String email, AuthRequestStatus status, String code);
    void update(AuthRequest authRequest);
    List<AuthRequest> findByStatus(AuthRequestStatus status);
}
