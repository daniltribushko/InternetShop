package org.example.userservice.repositories;

import org.example.userservice.models.entities.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 15.06.2024
 * <p>
 * Класс для тестирования репозитория для работы с пользователями
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    private final UserRepository userRepository;
    private LocalDateTime now;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
        now = LocalDateTime.now();
    }

    @BeforeAll
    void addUsers() {
        User user1 = new User("email 1", now, now);
        User user2 = new User("email 2", now, now);
        User user3 = new User("email 3", now, now);

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @AfterAll
    void deleteUsers() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void saveTest() {
        User user = new User("email 4", now, now);
        long count = userRepository.count();
        userRepository.save(user);
        long count2 = userRepository.count();

        Assertions.assertEquals(count + 1, count2);
    }

    @Test
    @Order(2)
    void findByIdTest() {
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(user);

        User user2 = userRepository.findById(user.getId()).orElse(null);

        Assertions.assertNotNull(user2);
        Assertions.assertEquals(user.getId(), user2.getId());
        Assertions.assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    @Order(3)
    void updateTest() {
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(user);

        user.setEmail("New email");
        userRepository.save(user);
        user = userRepository.findById(user.getId()).orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("New email", user.getEmail());
    }

    @Test
    @Order(4)
    void deleteTest() {
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(user);

        long count = userRepository.count();
        userRepository.delete(user);
        long count2 = userRepository.count();

        Assertions.assertEquals(count - 1, count2);
    }

    @Test
    @Order(5)
    void existByEmailTest() {
        Assertions.assertTrue(userRepository.existsByEmail("email 1"));
    }

    @Test
    @Order(6)
    void findByEmailTest() {
        User user = userRepository.findByEmail("email 1")
                .orElse(null);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("email 1", user.getEmail());
    }
}
