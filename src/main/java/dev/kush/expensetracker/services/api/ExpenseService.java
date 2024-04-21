package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.dtos.SaveExpenseDto;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    List<ExpenseDto> findAllExpensesByMemberId(Integer memberId);

    List<ExpenseDto> findAllExpensesByMemberEmail(String email);

    List<ExpenseDto> findAllExpensesByMemberIdAndDateRange(Integer memberId, LocalDate startDate, LocalDate endDate);

    List<ExpenseDto> findAllExpensesByMemberEmailAndDateRange(String email, LocalDate startDate, LocalDate endDate);

    ExpenseDto findExpenseById(Integer expenseId);

    ExpenseDto saveExpense(SaveExpenseDto saveExpenseDto);

    ExpenseDto updateExpense(SaveExpenseDto saveExpenseDto, Integer expenseId);

    int deleteExpense(Integer expenseId);
}
