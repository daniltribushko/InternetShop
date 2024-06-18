package org.example.userservice.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.userservice.exceptions.users.UserNotAdminException;
import org.example.userservice.exceptions.users.UserTryChangeAnotherUserException;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.db.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Аспект для проверки изменяет ли пользователь себя
 */
@Aspect
@Component
public class UsersAspects {
    private final UserDBService userDBService;

    @Autowired
    public UsersAspects(UserDBService userDBService) {
        this.userDBService = userDBService;
    }

    /**
     * Проверка не пытается ли пользователь изменить себя
     *
     * @param id    идентификатор пользователя, которого пытаются изменить
     * @param email адрес текущего пользователя
     */
    @Before(value = "@annotation(org.example.userservice.aspects.annotations" +
            ".CheckUserDontChangeAnotherUser) && args(id,email)", argNames = "id,email")
    public void checkUserDontChangeAnotherUser(Long id, String email) {
        User user = userDBService.findById(id);
        User currentUser = userDBService.findByEmail(email);
        if (!Objects.equals(user.getId(), currentUser.getId()) && !user.isAdmin()) {
            throw new UserTryChangeAnotherUserException(email, user.getEmail());
        }
    }

    /**
     * Проверка является ли пользователь администратором
     *
     * @param email адрес пользователч
     */
    @Before(value = "@annotation(org.example.userservice.aspects.annotations.CheckUserAdmin) && " +
            "args(email)", argNames = "email")
    public void checkUserAdmin(String email) {
        User user = userDBService.findByEmail(email);
        if (!user.isAdmin()) {
            throw new UserNotAdminException(email);
        }
    }
}
