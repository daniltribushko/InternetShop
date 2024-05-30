package org.example.userservice.services.db;

import org.example.userservice.models.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Сервис для работы с пользователями в бд
 */
public interface UserDBService extends DBService<User, Long> {
    User findByEmail(String email);
    boolean existByEmail(String email);
    User updateUser(User user);
}
