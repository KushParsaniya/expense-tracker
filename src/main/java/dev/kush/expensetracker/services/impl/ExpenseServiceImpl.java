package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.dtos.api.ExpenseDto;
import dev.kush.expensetracker.mapper.ExpenseMapper;
import dev.kush.expensetracker.models.entities.Expense;
import dev.kush.expensetracker.repos.ExpenseRepository;
import dev.kush.expensetracker.services.api.ExpenseService;
import dev.kush.expensetracker.services.api.FindOrCreateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final FindOrCreateService findOrCreateService;

    private final ExpenseMapper expenseMapper;


    @Override
    public List<ExpenseDto> findAllExpensesByMemberId(Integer memberId) {
        return expenseRepository.findAllExpensesByMemberId(memberId);
    }

    @Override
    public List<ExpenseDto> findAllExpensesByMemberEmail(String email) {
        return expenseRepository.findAllExpensesByMemberEmail(email);
    }

    @Override
    public List<ExpenseDto> findAllExpensesByMemberIdAndDateRange(Integer memberId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAllExpensesByMemberIdAndDateRange(memberId, startDate, endDate);
    }

    @Override
    public List<ExpenseDto> findAllExpensesByMemberEmailAndDateRange(String email, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAllExpensesByMemberEmailAndDateRange(email, startDate, endDate);
    }

    @Override
    public ExpenseDto findExpenseById(Integer expenseId) {
        return expenseRepository.findExpenseDtoById(expenseId).orElseThrow(
                () -> new EntityNotFoundException("Expense not found with id: " + expenseId)
        );
    }

    @Override
    @Transactional
    public ExpenseDto saveExpense(SaveExpenseDto saveExpenseDto) {
        // check whether the expense type exists or not
        Expense expense = expenseMapper.mapSaveExpenseDtoToExpense(saveExpenseDto);

        return expenseMapper.mapExpenseToExpenseDto(expenseRepository.save(expense));

    }

    @Override
    public ExpenseDto updateExpense(SaveExpenseDto saveExpenseDto, Integer expenseId) {
        Expense storedExpense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new EntityNotFoundException("Expense not found with id: " + expenseId)
        );

        if (isStringUpdatable(storedExpense.getDescription(), saveExpenseDto.description())) {
            storedExpense.setDescription(saveExpenseDto.description());
        }

        if (saveExpenseDto.amount() > 0 && storedExpense.getAmount().doubleValue() != saveExpenseDto.amount()) {
            storedExpense.setAmount(BigDecimal.valueOf(saveExpenseDto.amount()));
        }

        if (isStringUpdatable(storedExpense.getExpenseType().getTypeName(), saveExpenseDto.expenseTypeName())) {
            storedExpense.setExpenseType(findOrCreateService.findOrCreateExpenseType(saveExpenseDto.expenseTypeName()));
        }

        if (isStringUpdatable(storedExpense.getCreatedDate().toString(), saveExpenseDto.createdDate().toString())) {
            storedExpense.setCreatedDate(saveExpenseDto.createdDate());
        }

        return expenseMapper.mapExpenseToExpenseDto(expenseRepository.save(storedExpense));
    }

    private boolean isStringUpdatable(String oldValue, String newValue) {
        return newValue != null && !newValue.isBlank() && !oldValue.equals(newValue);
    }

    @Override
    public int deleteExpense(Integer expenseId) {
        return expenseRepository.deleteByExpenseId(expenseId);
    }


}
