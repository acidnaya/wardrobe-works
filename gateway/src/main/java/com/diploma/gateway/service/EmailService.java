package com.diploma.gateway.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetLink, String resetDeeplink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Сброс пароля");
            helper.setFrom("wardrobeworks@yandex.ru");

            String htmlContent = "<p>Вы запросили сброс пароля.</p>"
                    + "<p>Перейдите по ссылке, чтобы установить новый пароль:</p>"
                    + "<p><a href=\"" + resetLink + "\">Сбросить пароль</a></p>"
                    + "<p><a href=\"" + resetDeeplink + "\">Сбросить пароль для iOS</a></p>"
                    + "<p>Если ссылка не работает, скопируйте и вставьте её в адресную строку браузера:</p>"
                    + "<p><code>" + resetDeeplink + "</code></p>"
                    + "<p>Ссылка действительна в течение 10 минут.</p>";

            helper.setText(htmlContent, true); // true для HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Не удалось отправить письмо: " + e.getMessage(), e);
        }
    }
}
