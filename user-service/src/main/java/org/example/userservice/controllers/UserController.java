package org.example.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.userservice.models.dto.request.UserRequest;
import org.example.userservice.models.dto.response.AllUserResponse;
import org.example.userservice.models.dto.response.ExceptionResponse;
import org.example.userservice.models.dto.response.UserResponse;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 30.05.2024
 * <p>
 * Контроллер для работы с пользователями
 */
@CrossOrigin
@RestController
@Tag(name = "User Controller")
@RequestMapping("/users")
@SecurityRequirement(name = "jwtAuth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find by id", description = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable
                                                    Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Update", description = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User change another user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(Principal principal,
                                               @PathVariable
                                               @Min(value = 1, message = "Id can not be less than 1")
                                               Long id,
                                               @Valid
                                               UserRequest request) {
        return ResponseEntity.ok(userService.update(id, principal.getName(), request));
    }

    @Operation(summary = "Find all", description = "Find all users by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllUserResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<AllUserResponse> findAll(
            @Schema(description = "User's role",
                    name = "role",
                    type = "string",
                    example = "USER")
            @RequestParam(required = false)
            String role,
            @Schema(description = "User's creation date",
                    name = "creationDate",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime creationDate,
            @Schema(description = "User's update date",
                    name = "updateDat",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime updateDate,
            @Schema(description = "Min user's creation date",
                    name = "minCreationDate",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime minCreationDate,
            @Schema(description = "Max user's creation date",
                    name = "maxCreationDate",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime maxCreationDate,
            @Schema(description = "Min user's update date",
                    name = "minUpdateDate",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime minUpdateDate,
            @Schema(description = "Max user's update date",
                    name = "maxUpdateDate",
                    type = "string",
                    format = "date-time")
            @RequestParam(required = false)
            LocalDateTime maxUpdateDate) {
        return ResponseEntity.ok(new AllUserResponse(
                userService.findAll(role,
                        creationDate,
                        updateDate,
                        minCreationDate,
                        maxCreationDate,
                        minUpdateDate,
                        maxUpdateDate)));
    }

    @Operation(summary = "Add role", description = "Add new role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "409", description = "Role already added to user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User by id or role by name not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/roles/add")
    public ResponseEntity<UserResponse> addRole(@PathVariable
                                                @Min(value = 1, message = "Id can not be less than 1")
                                                Long id,
                                                Principal principal,
                                                @NotBlank(message = "Role can not be blank")
                                                String role) {
        return ResponseEntity.ok(userService.addRole(id, principal.getName(), role));
    }

    @Operation(summary = "Delete role", description = "Delete role from user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "409", description = "Role not found in user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User by id or role by name not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/roles/delete")
    public ResponseEntity<UserResponse> deleteRole(@PathVariable
                                                   @Min(value = 1, message = "Id can not be less than 1")
                                                   Long id,
                                                   Principal principal,
                                                   @NotBlank(message = "Role can not be blank")
                                                   String role) {
        return ResponseEntity.ok(userService.deleteRole(id, principal.getName(), role));
    }

    @Operation(summary = "Delete", description = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable
                                    @Min(value = 1, message = "Id can not be less than 1")
                                    Long id,
                                    Principal principal) {
        userService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
