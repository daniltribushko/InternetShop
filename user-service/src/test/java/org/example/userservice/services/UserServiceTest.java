package org.example.userservice.services;

import org.example.userservice.exceptions.roles.RoleAlreadyAddedException;
import org.example.userservice.exceptions.roles.UserDontHaveRoleException;
import org.example.userservice.exceptions.users.UserAlreadyExistException;
import org.example.userservice.models.dto.request.UserRequest;
import org.example.userservice.models.dto.response.RoleResponse;
import org.example.userservice.models.dto.response.UserResponse;
import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.db.RoleServiceDb;
import org.example.userservice.services.db.UserDBService;
import org.example.userservice.services.imp.UserServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDBService userDBService;
    @Mock
    private RoleServiceDb roleServiceDb;
    @InjectMocks
    private UserServiceImp userService;

    private List<UserResponse> usersResponse;
    private List<User> users;

    @BeforeEach
    void addUsers() {
        List<RoleResponse> roles = List.of(new RoleResponse(1, "USER"),
                new RoleResponse(2, "ADMIN"));
        UserResponse response1 = UserResponse.builder()
                .id(1L)
                .email("Email 1")
                .roles(roles)
                .build();

        UserResponse response2 = UserResponse.builder()
                .id(2L)
                .email("Email 2")
                .roles(roles)
                .build();

        UserResponse response3 = UserResponse.builder()
                .id(3L)
                .email("Email 3")
                .roles(roles)
                .build();

        Role role1 = new Role("USER");
        role1.setId(1);
        Role role2 = new Role("ADMIN");
        role2.setId(2);
        Set<Role> rolesSet = new HashSet<>(Set.of(role1, role2));

        User user = new User("Email 1", null, null);
        user.setId(1L);
        user.setRoles(rolesSet);
        User user2 = new User("Email 2", null, null);
        user2.setId(2L);
        user2.setRoles(rolesSet);
        User user3 = new User("Email 3", null, null);
        user3.setId(3L);
        user3.setRoles(Set.of(role1));

        users = List.of(user, user2, user3);
        usersResponse = List.of(response1, response2, response3);
    }

    @Test
    void findByIdTest(){
        Mockito.when(userDBService.findById(1L)).thenReturn(users.get(0));

        UserResponse actual = userService.findById(1L);
        UserResponse expected = usersResponse.get(0);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getRoles().size(), actual.getRoles().size());
    }

    @Test
    void updateTest(){
        Mockito.when(userDBService.findById(2L))
                .thenReturn(users.get(1));
        Mockito.when(userDBService.existByEmail("New email"))
                .thenReturn(false);
        UserRequest userRequest = new UserRequest("New email");
        UserResponse expected = usersResponse.get(1);
        expected.setEmail("New email");
        UserResponse actual = userService.update(2L, "Email", userRequest);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime actualTime = actual.getUpdateDate();

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(now.getHour(), actualTime.getHour());
        Assertions.assertEquals(now.getMinute(), actualTime.getMinute());
    }

    @Test
    void updateExceptionTest(){
        Mockito.when(userDBService.findById(1L))
                .thenReturn(users.get(0));
        Mockito.when(userDBService.existByEmail("New email"))
                .thenReturn(true);

        UserAlreadyExistException exception = Assertions.assertThrows(
                UserAlreadyExistException.class,
                () -> userService.update(1L, "Email", new UserRequest("New email")));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("User: New email already exist",
                exception.getMessage());
    }

    @Test
    void findAllTest(){
        Mockito.when(userDBService.findAll())
                .thenReturn(users);

        List<UserResponse> actualAdmin = userService.findAll("ADMIN",
                null,
                null,
                null,
                null,
                null,
                null);

        List<UserResponse> actualUser = userService.findAll("USER",
                null,
                null,
                null,
                null,
                null,
                null);

        List<UserResponse> actualNull = userService.findAll("ROLE",
                null,
                null,
                null,
                null,
                null,
                null);

        Assertions.assertEquals(2, actualAdmin.size());
        Assertions.assertEquals(3, actualUser.size());
        Assertions.assertEquals(0, actualNull.size());
    }

    @Test
    void addRoleTest(){
        Role role = new Role("New Role");
        Mockito.when(userDBService.findById(2L))
                .thenReturn(users.get(1));
        Mockito.when(roleServiceDb.findByName("New Role"))
                .thenReturn(role);
        UserResponse expected = usersResponse.get(1);
        UserResponse actual = userService.addRole(2L,
                "Email 1",
                "New Role");

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertNotNull(actual.getRoles()
                .stream()
                .filter(r -> Objects.equals(r.getName(), "New Role"))
                .findFirst()
                .orElse(null));
    }

    @Test
    void addRoleTestException(){
        Role role = new Role("New Role");
        User user = users.get(0);
        Set<Role> roles = user.getRoles();
        roles.add(role);

        Mockito.when(userDBService.findById(1L))
                .thenReturn(users.get(0));
        Mockito.when(roleServiceDb.findByName("New Role"))
                .thenReturn(role);

        RoleAlreadyAddedException exception = Assertions.assertThrows(
                RoleAlreadyAddedException.class,
                () -> userService.addRole(1L, null, "New Role"));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("Role: New Role already added to user: Email 1",
                exception.getMessage());
    }

    @Test
    void deleteRoleTest(){
        Role role = new Role("USER");
        role.setId(1);
        User user = users.get(0);
        Mockito.when(userDBService.findById(1L))
                .thenReturn(user);
        Mockito.when(roleServiceDb.findByName("USER"))
                .thenReturn(role);

        UserResponse actual = userService.deleteRole(1L, null, "USER");
        UserResponse expected = usersResponse.get(0);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(1, actual.getRoles().size());
    }

    @Test
    void deleteRoleExceptionTest(){
        Role role = new Role("NEW ROLE");
        Mockito.when(userDBService.findById(1L))
                .thenReturn(users.get(0));
        Mockito.when(roleServiceDb.findByName("NEW ROLE"))
                .thenReturn(role);

        UserDontHaveRoleException exception = Assertions.assertThrows(
                UserDontHaveRoleException.class,
                () -> userService.deleteRole(1L, null, "NEW ROLE"));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("User: Email 1 don't have role: NEW ROLE", exception.getMessage());
    }
}
