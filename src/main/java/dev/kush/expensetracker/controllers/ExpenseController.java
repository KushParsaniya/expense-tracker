package dev.kush.expensetracker.controllers;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/member/{memberId}")
    public List<ExpenseDto> findAllExpensesByMemberId(@PathVariable Integer memberId) {
        return expenseService.findAllExpensesByMemberId(memberId);
    }

    @GetMapping("/member")
    public List<ExpenseDto> findAllExpensesByMemberEmail(@RequestParam String email) {
        return expenseService.findAllExpensesByMemberEmail(email);
    }

    @GetMapping("/member/{memberId}/date")
    public List<ExpenseDto> findAllExpensesByMemberIdAndDateRange(@PathVariable Integer memberId,
                                                                  @RequestParam LocalDate startDate,
                                                                  @RequestParam LocalDate endDate) {
        return expenseService.findAllExpensesByMemberIdAndDateRange(memberId, startDate, endDate);
    }

    @GetMapping("/member/date")
    public List<ExpenseDto> findAllExpensesByMemberEmailAndDateRange(@RequestParam String email,
                                                                     @RequestParam LocalDate startDate,
                                                                     @RequestParam LocalDate endDate) {
        return expenseService.findAllExpensesByMemberEmailAndDateRange(email, startDate, endDate);
    }

    @PostMapping
    public ExpenseDto saveExpense(@RequestBody SaveExpenseDto saveExpenseDto) {
        return expenseService.saveExpense(saveExpenseDto);
    }


}
