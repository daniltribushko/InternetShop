package org.example.userservice.repositories;

import org.example.userservice.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 27.05.2024
 * <p>
 * Репозиторий для работы с ролями пользователя
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
