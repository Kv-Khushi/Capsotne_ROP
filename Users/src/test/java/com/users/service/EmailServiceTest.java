package com.users.service;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testSendMail_ExceptionHandling() {
        // Prepare input data
        String from = "test@domain.com";
        String recipient = "recipient@domain.com";
        String text = "This is a test email.";
        var toList = Arrays.asList(recipient);

        // Simulate an exception when sending the email
        doThrow(new RuntimeException("Email send failure")).when(javaMailSender).send(any(MimeMessage.class));

        // Call the method
        emailService.sendMail(from, toList, text);

        // Verify that the exception was caught and didn't crash the method
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
