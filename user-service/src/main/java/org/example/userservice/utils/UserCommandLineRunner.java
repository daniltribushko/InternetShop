package org.example.userservice.utils;

import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.db.RoleServiceDb;
import org.example.userservice.services.db.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Класс для создания администратора в бд
 */
@Order(2)
@Component
public class UserCommandLineRunner implements CommandLineRunner {
    private final UserDBService userService;
    private final RoleServiceDb roleService;

    @Autowired
    public UserCommandLineRunner(UserDBService userService,
                                 RoleServiceDb roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userService.existByEmail("danil.tribushko@bk.ru")) {
            LocalDateTime now = LocalDateTime.now();
            User user = new User("danil.tribusko@bk.ru", now, now);
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName("ADMIN"));
            roles.add(roleService.findByName("USER"));
            roles.add(roleService.findByName("BRAND"));
            user.setRoles(roles);

            userService.save(user);
        }
    }
}
