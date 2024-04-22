package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.models.EmailDetails;
import dev.kush.expensetracker.services.api.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public ResponseEntity<String> sendEmail(EmailDetails emailDetails) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(sender);
        helper.setTo(emailDetails.recipient());
        helper.setSubject(emailDetails.subject());
        helper.setSentDate(new Date());
        helper.setText(emailDetails.msgBody(), true);

        javaMailSender.send(message);

        return new ResponseEntity<>("Successfully sent mail", HttpStatus.OK);

    }
}
