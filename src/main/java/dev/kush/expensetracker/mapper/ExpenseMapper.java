package dev.kush.expensetracker.mapper;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.models.entities.Expense;

public interface ExpenseMapper {
    Expense mapSaveExpenseDtoToExpense(SaveExpenseDto saveExpenseDto);

    ExpenseDto mapExpenseToExpenseDto(Expense expense);
}
