package org.example.userservice.repositories;

import org.example.userservice.models.entities.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 15.06.2024
 * <p>
 * Класс для тестирования репозитория для работы с ролями
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleRepositoryTest {
    private final RoleRepository roleRepository;

    @Autowired
    RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @BeforeAll
    void addRoles() {
        Role role = new Role("Role 1");
        Role role2 = new Role("Role 2");
        Role role3 = new Role("Role 3");

        roleRepository.saveAll(List.of(role, role2, role3));
    }

    @AfterAll
    void deleteRoles() {
        roleRepository.deleteAll();
    }

    @Test
    @Order(1)
    void saveTest() {
        Role role = new Role("Role 4");
        long count = roleRepository.count();
        roleRepository.save(role);
        long newCount = roleRepository.count();

        Assertions.assertEquals(count + 1, newCount);
    }

    @Test
    @Order(2)
    void findByIdTest() {
        Role role = roleRepository.findAll()
                .stream()
                .findFirst()
                .get();
        Role role2 = roleRepository.findById(role.getId())
                .get();

        Assertions.assertEquals(role.getId(), role2.getId());
        Assertions.assertEquals(role.getName(), role2.getName());
    }

    @Test
    @Order(3)
    void updateTest() {
        Role role = roleRepository.findAll()
                .stream()
                .findFirst()
                .get();
        role.setName("New name");
        roleRepository.save(role);
        role = roleRepository.findById(role.getId())
                .get();

        Assertions.assertEquals("New name", role.getName());
    }

    @Test
    @Order(4)
    void deleteTest() {
        Role role = roleRepository.findAll()
                .stream()
                .findFirst()
                .get();

        long count = roleRepository.count();
        roleRepository.delete(role);
        long newCount = roleRepository.count();

        Assertions.assertEquals(count - 1, newCount);
    }

    @Test
    @Order(5)
    void findByNameTest() {
        Optional<Role> role = roleRepository.findByName("Role 1");
        Optional<Role> role2 = roleRepository.findByName("Role 100");

        Assertions.assertFalse(role2.isPresent());
        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals("Role 1", role.get().getName());
    }
}
