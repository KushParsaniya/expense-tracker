package dev.kush.expensetracker.models;

public record EmailDetails(
        String recipient,
        String msgBody,
        String subject
) {
}
