package org.example.userservice.services.imp;

import org.example.userservice.aspects.annotations.CheckUserAdmin;
import org.example.userservice.aspects.annotations.CheckUserDontChangeAnotherUser;
import org.example.userservice.exceptions.roles.RoleAlreadyAddedException;
import org.example.userservice.exceptions.roles.UserDontHaveRoleException;
import org.example.userservice.exceptions.users.UserAlreadyExistException;
import org.example.userservice.models.dto.request.UserRequest;
import org.example.userservice.models.dto.response.RoleResponse;
import org.example.userservice.models.dto.response.UserResponse;
import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.UserService;
import org.example.userservice.services.db.RoleServiceDb;
import org.example.userservice.services.db.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Реализация сервиса для работы с пользователями
 */
@Service
public class UserServiceImp implements UserService {
    private final UserDBService userDBService;
    private final RoleServiceDb roleServiceDb;

    @Autowired
    public UserServiceImp(UserDBService userDBService,
                          RoleServiceDb roleServiceDb) {
        this.userDBService = userDBService;
        this.roleServiceDb = roleServiceDb;
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userDBService.findById(id);
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(RoleResponse::mapFromEntity)
                        .toList())
                .creationDate(user.getCreationDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

    @Override
    @CheckUserDontChangeAnotherUser
    public UserResponse update(Long id, String email, UserRequest userRequest) {
        User user = userDBService.findById(id);
        if (userDBService.existByEmail(email)) {
            throw new UserAlreadyExistException(email);
        }
        user.setEmail(userRequest.getEmail());
        user.setUpdateDate(LocalDateTime.now());
        userDBService.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(RoleResponse::mapFromEntity)
                        .toList())
                .creationDate(user.getCreationDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

    @Override
    public List<UserResponse> findAll(String role,
                                      LocalDateTime creationDate,
                                      LocalDateTime updateDate,
                                      LocalDateTime minCreationDate,
                                      LocalDateTime maxCreationDate,
                                      LocalDateTime minUpdateDate,
                                      LocalDateTime maxUpdateDate) {
        return userDBService.findAll()
                .stream()
                .filter(u -> (role == null || isUserHasRole(u, role)) &&
                        (creationDate == null || Objects.equals(creationDate, u.getCreationDate())) &&
                        (updateDate == null || Objects.equals(updateDate, u.getUpdateDate())) &&
                        (minCreationDate == null || u.getCreationDate().isAfter(minCreationDate)) &&
                        (maxCreationDate == null || u.getCreationDate().isBefore(maxCreationDate)) &&
                        (minUpdateDate == null || u.getUpdateDate().isAfter(minUpdateDate)) &&
                        (maxUpdateDate == null || u.getUpdateDate().isBefore(maxUpdateDate)))
                .map(u -> UserResponse.builder()
                        .id(u.getId())
                        .email(u.getEmail())
                        .creationDate(u.getCreationDate())
                        .updateDate(u.getUpdateDate())
                        .roles(u.getRoles()
                                .stream()
                                .map(RoleResponse::mapFromEntity)
                                .toList())
                        .build())
                .toList();
    }

    private boolean isUserHasRole(User user, String role) {
        boolean result = false;
        Set<Role> roles = user.getRoles();
        for (Role r : roles) {
            if (r.getName().equals(role)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    @CheckUserAdmin
    public void delete(Long id, String email) {
        userDBService.delete(id);
    }

    @Override
    @CheckUserAdmin
    public UserResponse addRole(Long id, String email, String role) {
        User user = userDBService.findById(id);
        Role roleEntity = roleServiceDb.findByName(role);
        String userEmail = user.getEmail();
        Set<Role> roles = user.getRoles();

        if (roles.contains(roleEntity)) {
            throw new RoleAlreadyAddedException(userEmail, role);
        }

        roles.add(roleEntity);
        user.setRoles(roles);
        user.setUpdateDate(LocalDateTime.now());
        userDBService.updateUser(user);

        return UserResponse.builder()
                .id(id)
                .email(userEmail)
                .creationDate(user.getCreationDate())
                .updateDate(user.getUpdateDate())
                .roles(roles.stream()
                        .map(RoleResponse::mapFromEntity)
                        .toList())
                .build();
    }

    @Override
    @CheckUserAdmin
    public UserResponse deleteRole(Long id, String email, String role) {
        User user = userDBService.findById(id);
        Role roleEntity = roleServiceDb.findByName(role);
        String userEmail = user.getEmail();
        Set<Role> roles = user.getRoles();
        user.setUpdateDate(LocalDateTime.now());

        if (!roles.contains(roleEntity)) {
            throw new UserDontHaveRoleException(userEmail, role);
        }

        roles.remove(roleEntity);
        user.setRoles(roles);
        userDBService.updateUser(user);

        return UserResponse.builder()
                .id(id)
                .email(userEmail)
                .creationDate(user.getCreationDate())
                .updateDate(user.getUpdateDate())
                .roles(roles.stream()
                        .map(RoleResponse::mapFromEntity)
                        .toList())
                .build();
    }
}
