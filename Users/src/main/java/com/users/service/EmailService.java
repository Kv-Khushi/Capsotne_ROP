package com.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Service for sending emails.
 */
@Service
public class EmailService {

    /**
     * JavaMailSender used to send emails.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Sends an email to a list of recipients.
     *
     * @param from the email address of the sender
     * @param to the list of recipient email addresses
     * @param text the body of the email
     */
    public void sendMail(final String from, final List<String> to, final String text) {
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
