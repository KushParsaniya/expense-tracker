package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.AuthenticationDto;

public interface Base64Service {
    AuthenticationDto decode(String base64Encoded);
}
