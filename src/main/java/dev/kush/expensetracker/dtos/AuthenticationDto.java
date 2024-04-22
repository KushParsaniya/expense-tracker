package dev.kush.expensetracker.dtos;

public record AuthenticationDto(
        String email,
        String password
) {
}
