package ca.sait.backup.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    void sendEmail(String to, String subject,String text);
}
