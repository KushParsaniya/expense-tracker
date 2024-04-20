package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.dtos.ExpenseDtoImpl;
import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.models.Expense;
import dev.kush.expensetracker.models.Member;
import dev.kush.expensetracker.repos.ExpenseRepository;
import dev.kush.expensetracker.repos.MemberRepository;
import dev.kush.expensetracker.services.ExpenseService;
import dev.kush.expensetracker.services.FindOrCreateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final FindOrCreateService findOrCreateService;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

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
    public ExpenseDto saveExpense(SaveExpenseDto saveExpenseDto) {
        // check whether the expense type exists or not
        // TODO: extract this method in ExpenseTypeService
        var expenseType = findOrCreateService.findOrCreateExpenseType(saveExpenseDto.expenseTypeName());
        Expense expense = new Expense();

        // TODO: extract this in MemberService
        Member member = memberRepository.findById(saveExpenseDto.memberId()).orElseThrow(
                () -> new EntityNotFoundException("Member not found with id: " + saveExpenseDto.memberId()));


        // TODO: create custome mapper for this
        expense.setAmount(BigDecimal.valueOf(saveExpenseDto.amount()));
        expense.setCreatedDate(saveExpenseDto.createdDate());
        expense.setCreatedTime(LocalTime.now());
        expense.setDescription(saveExpenseDto.description());
        expense.setExpenseType(expenseType);
        expense.setMember(member);
        member.setExpenses(List.of(expense));

        expense = expenseRepository.save(expense);
        return new ExpenseDtoImpl(expense.getExpenseId(),expense.getAmount(),expense.getDescription(),expense.getCreatedDate(),expense.getExpenseType().getTypeName(),expense.getCreatedTime());

    }

    @Override
    public ExpenseDto updateExpense(SaveExpenseDto saveExpenseDto) {
        // TODO: write update expense
        return null;
    }

    @Override
    public int deleteExpense(Integer expenseId) {
        return expenseRepository.deleteByExpenseId(expenseId);
    }
}
