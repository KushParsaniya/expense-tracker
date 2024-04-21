package dev.kush.expensetracker.dtos;

public record ResponseDto(
        String message,
        Object data,
        int status
) {
}
