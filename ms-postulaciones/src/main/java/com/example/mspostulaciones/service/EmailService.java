package com.example.mspostulaciones.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Indicando que el cuerpo es HTML

            mailSender.send(message);
            System.out.println("Correo enviado exitosamente a: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo a " + toEmail + ": " + e.getMessage());
        }
    }
}

