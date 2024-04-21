package dev.kush.expensetracker.mapper.impl;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.dtos.impl.ExpenseDtoImpl;
import dev.kush.expensetracker.mapper.ExpenseMapper;
import dev.kush.expensetracker.models.entities.Expense;
import dev.kush.expensetracker.repos.MemberRepository;
import dev.kush.expensetracker.services.api.FindOrCreateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ExpenseMapperImpl implements ExpenseMapper {

    private final FindOrCreateService findOrCreateService;

    private final MemberRepository memberRepository;


    @Override
    public Expense mapSaveExpenseDtoToExpense(SaveExpenseDto saveExpenseDto) {
        return new Expense(
                BigDecimal.valueOf(saveExpenseDto.amount()),
                saveExpenseDto.description(),
                saveExpenseDto.createdDate(),
                LocalTime.now(),
                findOrCreateService.findOrCreateExpenseType(saveExpenseDto.expenseTypeName()),
                memberRepository.findById(saveExpenseDto.memberId()).orElseThrow(
                        () -> new EntityNotFoundException("member with id " + saveExpenseDto.memberId() + " not found"))
        );
    }

    @Override
    public ExpenseDto mapExpenseToExpenseDto(Expense expense) {
        return new ExpenseDtoImpl(expense.getExpenseId(),expense.getAmount(),
                expense.getDescription(),expense.getCreatedDate(),
                expense.getExpenseType().getTypeName(),expense.getCreatedTime());
    }
}
