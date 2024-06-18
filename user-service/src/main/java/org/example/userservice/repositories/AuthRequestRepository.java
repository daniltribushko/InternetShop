package org.example.userservice.repositories;

import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Репозиторий для работы с запросами на авторизацию
 */
@Repository
public interface AuthRequestRepository extends JpaRepository<AuthRequest, Long> {
    Optional<AuthRequest> findByEmailAndStatusAndCode(String email, AuthRequestStatus status, String code);
    boolean existsByEmailAndStatusAndCode(String email, AuthRequestStatus status, String code);
    List<AuthRequest> findAllByStatus(AuthRequestStatus status);
}
