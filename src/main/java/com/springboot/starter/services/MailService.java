package com.springboot.starter.services;

import com.springboot.starter.configs.EmailProperties;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.springboot.starter.SpringBootStarterApplication.LOGGER;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Async
    public void sendMailToMany(String[] emails, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emails);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Async
    public void sendMail(String email, String subject, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(emailProperties.getFrom());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);

            LOGGER.info("Email sent!");
        }
        catch (Exception ex) {
            LOGGER.info("The email was not sent. Error message: "+ex.getMessage());
        }

    }

}
