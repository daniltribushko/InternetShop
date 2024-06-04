package org.example.goods.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.goods.exceptions.users.UserNotAdminException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Ttribushko Danil
 * @since 04.06.2024
 * <p>
 * Аспект для работы с пользователями
 */
@Aspect
@Component
public class UsersAspect {
    @Before(value = "@annotation(org.example.goods.aspect.annotation.CheckUserAdmin) && " +
            "args(email,..)", argNames = "email")
    public void checkUserIsAdmin(String email) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user.getName());
        if (!user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                !Objects.equals(email, user.getName())) {
            throw new UserNotAdminException(email);
        }
    }
}
