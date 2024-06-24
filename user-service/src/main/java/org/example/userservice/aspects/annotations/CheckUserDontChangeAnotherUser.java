package org.example.userservice.aspects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tribushko Danil
 * @since 31.05.2024
 * <p>
 * Аннотация для методов, которые надо проверить
 * не изменяет ли пользователь другого пользователя
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUserDontChangeAnotherUser {
}
