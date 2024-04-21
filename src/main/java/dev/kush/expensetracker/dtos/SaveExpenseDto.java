package dev.kush.expensetracker.dtos;

import java.time.LocalDate;

public record SaveExpenseDto(
        Double amount,
        String description,
        String expenseTypeName,
        Integer memberId,
        LocalDate createdDate
) {
}
