package org.example.userservice.utils;

import org.example.userservice.models.entities.Role;
import org.example.userservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Класс для заполнения ролей в бд, при запуске приложения
 */
@Component
public class RoleCommandLineRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleCommandLineRunner(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(new Role("ADMIN"),
                    new Role("USER"),
                    new Role("BRAND")));
        }
    }
}
