package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.dtos.AuthenticationDto;
import dev.kush.expensetracker.services.api.Base64Service;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Base64EncodedImpl implements Base64Service {

    @Override
    public AuthenticationDto decode(String base64Encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);

        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] splitedString = decodedString.split(":");

        if (splitedString.length > 2) {
            return new AuthenticationDto(splitedString[0], splitedString[1]);
        } else {
            throw new IllegalArgumentException("Invalid base64 encode String");
        }
    }
}
