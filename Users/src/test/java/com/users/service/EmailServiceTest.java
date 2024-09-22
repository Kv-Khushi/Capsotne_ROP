package com.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    private List<String> recipientEmails;
    private String subject;
    private String customerName;
    private String customMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipientEmails = Arrays.asList("recipient1@example.com", "recipient2@example.com");
        subject = "Contact Us Inquiry";
        customerName = "Test Name";
        customMessage = "This is a test message.";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testSendContactUsEmailSuccess() throws Exception {

        ReflectionTestUtils.setField(emailService, "senderEmail", "vyaskhushi2407@example.com");
        ReflectionTestUtils.setField(emailService, "supportName", "Khushi Vyas");

        emailService.sendContactUsEmail(recipientEmails, subject, customerName, customMessage);

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(recipientEmails.size())).send(mimeMessageCaptor.capture());


        MimeMessage capturedMimeMessage = mimeMessageCaptor.getValue();
        MimeMessageHelper helper = new MimeMessageHelper(capturedMimeMessage, true);

        String expectedContent = "Hello Test Name!\nThis is a test message.\nBest regards,\nKhushi Vyas\n";

    }


    @Test
    void testSendContactUsEmailFailure() {

        doThrow(new RuntimeException("Failed to send email")).when(mailSender).send(any(MimeMessage.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                emailService.sendContactUsEmail(recipientEmails, subject, customerName, customMessage));

        assertEquals("From address must not be null", exception.getMessage());
    }
}
