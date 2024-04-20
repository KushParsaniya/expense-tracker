package dev.kush.expensetracker.exceptions;

public record ErrorMessage(String message, int status, long timestamp) {
}
