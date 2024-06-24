package org.example.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.userservice.exceptions.GlobalAppException;
import org.example.userservice.exceptions.authrequest.AuthRequestByEmailAndStatusAndCodeNotFoundException;
import org.example.userservice.exceptions.email.MailDidntSendException;
import org.example.userservice.models.dto.request.CreateTokenRequest;
import org.example.userservice.models.dto.response.ExceptionResponse;
import org.example.userservice.models.dto.response.TokenResponse;
import org.example.userservice.models.dto.response.TokenValidResponse;
import org.example.userservice.models.dto.response.UserResponse;
import org.example.userservice.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Контроллер для автоизации пользователей
 */
@RestController()
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Auth Controller")
public class AuthRequestController {
    private final AuthUserService authUserService;

    @Autowired
    public AuthRequestController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Operation(summary = "Login", description = "Send code on user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "code send",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Mail didn't sent",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Schema(
                    description = "User email",
                    name = "email",
                    type = "string",
                    example = "ivanov.ivan@gmail.com")
            @Email
            @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
            @RequestParam
            String email) {
        authUserService.sendCode(email);
        return ResponseEntity.ok("Code sanded");
    }

    @Operation(summary = "Confirm", description = "Confirm user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token received",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Auth request with email and status and code not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/confirm")
    public ResponseEntity<TokenResponse> getToken(@Valid
                                                  @RequestBody
                                                  CreateTokenRequest request) {
        String token = authUserService.getToken(request);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Operation(summary = "Is valid", description = "Check is token valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenValidResponse.class))),
            @ApiResponse(responseCode = "400", description = "Token not valid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/valid")
    public ResponseEntity<TokenValidResponse> isTokenValid(@RequestParam
                                                           @NotBlank String token) {
        return ResponseEntity.ok(new TokenValidResponse(authUserService.isTokenValid(token)));
    }

    @Operation(summary = "Find by email", description = "Find user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User by email not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    ExceptionResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<UserResponse> findByEmail(@RequestParam
                                                    String email) {
        return ResponseEntity.ok(authUserService.findByEmail(email));
    }

}
