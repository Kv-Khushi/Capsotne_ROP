package com.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String from, List<String> to, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            // Convert List<String> to array
            helper.setTo(to.toArray(new String[0]));
            helper.setText(text);
            System.out.println(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }
    }
}