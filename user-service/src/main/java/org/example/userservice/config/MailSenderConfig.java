
package org.example.userservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Класс конфигурации для отправки электонных сообщений
 */

@Configuration
@PropertySource("classpath:secret.properties")
public class MailSenderConfig {
    @Value("${gmail.email}")
    private String email;
    @Value("${gmail.password}")
    public String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setUsername(email);
        mailSender.setPassword(password);
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(587);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

