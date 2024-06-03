package org.example.userservice.services.imp;

import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.dto.request.CreateTokenRequest;
import org.example.userservice.models.dto.response.RoleResponse;
import org.example.userservice.models.dto.response.UserResponse;
import org.example.userservice.models.entities.AuthRequest;
import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.AuthUserService;
import org.example.userservice.services.db.AuthRequestDBService;
import org.example.userservice.services.db.RoleServiceDb;
import org.example.userservice.services.db.UserDBService;
import org.example.userservice.utils.JwtTokenUtil;
import org.example.userservice.utils.MailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Реализация сервиса для автоизации пользователей
 */
@Service
public class AuthUserServiceImp implements AuthUserService {
    private final MailSenderUtil mailSender;
    private final UserDBService userDBService;
    private final RoleServiceDb roleServiceDb;
    private final AuthRequestDBService authRequestDBService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthUserServiceImp(MailSenderUtil mailSender,
                              UserDBService userDBService,
                              RoleServiceDb roleServiceDb,
                              AuthRequestDBService authRequestDBService,
                              JwtTokenUtil jwtTokenUtil) {
        this.mailSender = mailSender;
        this.userDBService = userDBService;
        this.roleServiceDb = roleServiceDb;
        this.authRequestDBService = authRequestDBService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void sendCode(String email) {
        if (!userDBService.existByEmail(email)) {
            LocalDateTime now = LocalDateTime.now();
            User user = new User(email, now, now);
            Set<Role> roles = new HashSet<>();
            roles.add(roleServiceDb.findByName("USER"));
            user.setRoles(roles);
            userDBService.save(user);
        }
        String code = createCode();

        authRequestDBService.save(AuthRequest.builder()
                .email(email)
                .status(AuthRequestStatus.ACTIVE)
                .code(code)
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        mailSender.sendMail(email, "Код подтверждения", "Код для получения токена: " + code);
    }

    @Override
    public String getToken(CreateTokenRequest request) {
        String email = request.getEmail();
        AuthRequest authRequest = authRequestDBService.findByEmailAndStatusAndCode(email,
                AuthRequestStatus.ACTIVE,
                request.getCode());
        String token = jwtTokenUtil.generateToken(userDBService.findByEmail(email));
        authRequest.setStatus(AuthRequestStatus.COMPLETED);
        authRequestDBService.update(authRequest);
        return token;
    }

    @Override
    public boolean isTokenValid(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        return jwtTokenUtil.isTokenValid(token, email);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userDBService.findByEmail(email);

        return UserResponse.builder()
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(RoleResponse::mapFromEntity)
                        .toList())
                .build();
    }

    private String createCode() {
        String chars = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
}
