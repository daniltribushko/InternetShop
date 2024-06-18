package org.example.userservice.services.db;

import org.example.userservice.exceptions.users.UserAlreadyExistException;
import org.example.userservice.exceptions.users.UserByEmailNotFoundException;
import org.example.userservice.exceptions.users.UserByIdNotFoundException;
import org.example.userservice.models.entities.User;
import org.example.userservice.repositories.UserRepository;
import org.example.userservice.services.db.imp.UserDBServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 17.06.2024
 * <p>
 * Класс для тестирования сервиса для работы с пользователями в бд
 */
@ExtendWith(SpringExtension.class)
class UserDBServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDBServiceImp userDBService;

    private LocalDateTime now;
    private List<User> users;

    UserDBServiceTest() {
        now = LocalDateTime.now();
    }

    @BeforeEach
    void addUsers() {
        User user1 = new User("email 1", now, now);
        user1.setId(1L);
        User user2 = new User("email 2", now, now);
        user2.setId(2L);
        User user3 = new User("email 3", now, now);
        user3.setId(3L);

        users = List.of(user1, user2, user3);
    }

    @Test
    void saveTest() {
        Mockito.when(userRepository.existsByEmail("email 1"))
                .thenReturn(false);
        userDBService.save(users.get(0));
    }

    @Test
    void saveExceptionTest() {
        User user = new User("email 4", now, now);
        Mockito.when(userRepository.existsByEmail("email 4"))
                .thenReturn(true);

        UserAlreadyExistException exception = Assertions.assertThrows(
                UserAlreadyExistException.class,
                () -> userDBService.save(user));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("User: email 4 already exist", exception.getMessage());
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        UserByIdNotFoundException exception = Assertions.assertThrows(
                UserByIdNotFoundException.class,
                () -> userDBService.findById(1L));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void findByIdTest() {
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(users.get(0)));
        User user = userDBService.findById(1L);

        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("email 1", user.getEmail());
    }

    @Test
    void deleteTest() {
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(users.get(0)));
        userDBService.delete(1L);
    }

    @Test
    void findByEmailTest() {
        Mockito.when(userRepository.findByEmail("email 1"))
                .thenReturn(Optional.of(users.get(0)));

        User actual = userDBService.findByEmail("email 1");

        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("email 1", actual.getEmail());
    }

    @Test
    void findByEmailExceptionTest() {
        Mockito.when(userRepository.findByEmail("email 1"))
                .thenReturn(Optional.empty());

        UserByEmailNotFoundException exception = Assertions.assertThrows(UserByEmailNotFoundException.class,
                () -> userDBService.findByEmail("email 1"));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("User: email 1 not found", exception.getMessage());
    }

    @Test
    void existByEmailTest() {
        Mockito.when(userRepository.existsByEmail("email 1"))
                .thenReturn(true);
        Mockito.when(userRepository.existsByEmail("email 2"))
                .thenReturn(false);

        Assertions.assertTrue(userDBService.existByEmail("email 1"));
        Assertions.assertFalse(userDBService.existByEmail("email 2"));
    }
}
