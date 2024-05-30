package org.example.userservice.utils;

import org.example.userservice.exceptions.email.MailDidntSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Утилитный класс для отправки сообщений
 */
@Service
@PropertySource("classpath:secret.properties")
public class MailSenderUtil {
    private final JavaMailSender javaMailSender;
    @Value("${gmail.email}")
    private String email;

    @Autowired
    public MailSenderUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Отправка сообщения
     *
     * @param to адрес получателя
     * @param subject тема сообщения
     * @param text текст сообщения
     */
    public void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new MailDidntSendException(to, e.getLocalizedMessage());
        }
    }
}
