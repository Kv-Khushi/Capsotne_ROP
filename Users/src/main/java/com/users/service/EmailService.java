package com.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


/**
 * Implementation of the {@link EmailService} interface.
 * <p>
 * Provides functionality to send emails, specifically for "Contact Us" inquiries.
 * Uses Spring's {@link JavaMailSender} to handle the actual sending of emails.
 * </p>
 */
@Service

public class  EmailService {

    /**
     * {@link JavaMailSender} used to send emails.
     * <p>
     * This is auto-wired by Spring and used to create and send {@link MimeMessage} instances.
     * </p>
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Email address used as the sender in the emails.
     * <p>
     * This value is injected from application properties using the key {@code spring.mail.username}.
     * </p>
     */
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Name of the support team or individual handling the email inquiries.
     * <p>
     * This value is injected from application properties using the key {@code support.contact.name}.
     * </p>
     */
    @Value("${support.contact.name}")
    private String supportName;


    /**
     * Sends an email in response to a "Contact Us" form submission.
     * <p>
     * The email is sent to the specified recipients with the provided subject and message.
     * The sender and contact information are configured through application properties.
     * </p>
     *
     * @param recipientEmails a list of email addresses of the recipients
     * @param subject         the subject of the email
     * @param customerName    the name of the customer sending the message
     * @param customMessage   the message content to be included in the email
     */

    public void sendContactUsEmail(final List<String> recipientEmails, final String subject,
                                   final String customerName, final String customMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String emailBody = "Hello " + customerName + "!\n"
                    + customMessage + "\n"
                    + "Best regards,\n"
                    + supportName + "\n";


            helper.setFrom(senderEmail);
            helper.setSubject(subject);
            helper.setText(emailBody);

            for (String recipientEmail : recipientEmails) {
                helper.setTo(recipientEmail);
                mailSender.send(mimeMessage);
            }
        } catch (MessagingException | MailSendException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
