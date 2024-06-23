package org.example.goods.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Аннотация для методов, которые требуют проверить является ли пользователь брэндом
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUserBrand {
}
