package dev.kush.expensetracker.dtos;

import dev.kush.expensetracker.dtos.api.ExpenseDto;

import java.util.List;

public record LoginResponse(String jwtToken, List<ExpenseDto> expenses) {
}
