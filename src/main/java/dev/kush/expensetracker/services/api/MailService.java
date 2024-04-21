package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.models.EmailDetails;
import org.springframework.http.ResponseEntity;

public interface MailService {
    ResponseEntity<String> sendEmail(EmailDetails emailDetails) throws Exception;
}
