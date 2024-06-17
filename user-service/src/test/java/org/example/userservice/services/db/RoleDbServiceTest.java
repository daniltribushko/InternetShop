package org.example.userservice.services.db;

import org.example.userservice.exceptions.roles.RoleByNameNotFoundException;
import org.example.userservice.models.entities.Role;
import org.example.userservice.repositories.RoleRepository;
import org.example.userservice.services.db.imp.RoleDbServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 17.06.2024
 * <p>
 * Класс для тестирования сервиса для работы с ролями
 */
@ExtendWith(MockitoExtension.class)
class RoleDbServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleDbServiceImp roleDbService;

    @Test
    void findByNameTest(){
        Role role = new Role("Role 1");
        Role role2 = new Role("Role 2");

        Mockito.when(roleRepository.findByName("Role 1"))
                .thenReturn(Optional.of(role));
        Mockito.when(roleRepository.findByName("Role 2"))
                .thenReturn(Optional.of(role2));

        Role actual = roleDbService.findByName("Role 1");
        Role actual2 = roleDbService.findByName("Role 2");

        Assertions.assertEquals("Role 1", actual.getName());
        Assertions.assertEquals("Role 2", actual2.getName());
    }

    @Test
    void findByNameExceptionTest(){
        Mockito.when(roleRepository.findByName("Role 1"))
                .thenReturn(Optional.empty());

        RoleByNameNotFoundException exception = Assertions.assertThrows(
                RoleByNameNotFoundException.class,
                () -> roleDbService.findByName("Role 1"));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("Role: Role 1 not found", exception.getMessage());
    }
}
