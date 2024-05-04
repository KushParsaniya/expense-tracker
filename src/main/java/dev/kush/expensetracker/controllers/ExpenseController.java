package dev.kush.expensetracker.controllers;

import dev.kush.expensetracker.dtos.ResponseDto;
import dev.kush.expensetracker.dtos.SaveExpenseDto;
import dev.kush.expensetracker.services.api.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/member/{memberId}")
    public ResponseDto findAllExpensesByMemberId(@PathVariable Integer memberId) {
        return new ResponseDto("ok", expenseService.findAllExpensesByMemberId(memberId), 200);
    }

    @GetMapping("/member")
    public ResponseDto findAllExpensesByMemberEmail(@RequestParam String email) {
        return new ResponseDto("ok", expenseService.findAllExpensesByMemberEmail(email), 200);
    }

    @GetMapping("/member/id/date/")
    public ResponseDto findAllExpensesByMemberIdAndDateRange(@RequestParam Integer memberId,
                                                             @RequestParam LocalDate startDate,
                                                             @RequestParam LocalDate endDate) {
        return new ResponseDto("ok", expenseService.findAllExpensesByMemberIdAndDateRange(memberId, startDate, endDate), 200);
    }

    @GetMapping("/member/email/date")
    public ResponseDto findAllExpensesByMemberEmailAndDateRange(@RequestParam String email,
                                                                @RequestParam LocalDate startDate,
                                                                @RequestParam LocalDate endDate) {
        return new ResponseDto("ok", expenseService.findAllExpensesByMemberEmailAndDateRange(email, startDate, endDate), 200);
    }

    @PostMapping
    public ResponseDto saveExpense(@RequestBody SaveExpenseDto saveResponseDto) {
        return new ResponseDto("ok", expenseService.saveExpense(saveResponseDto), 201);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseDto deleteExpense(@PathVariable Integer expenseId) {
        var rowsDeleted = expenseService.deleteExpense(expenseId);

        if (rowsDeleted == 0) {
            return new ResponseDto("error", "Expense not found with id: " + expenseId, 404);
        }

        return new ResponseDto("ok", "Expense deleted successfully", 200);
    }

    @PutMapping("/{expenseId}")
    public ResponseDto updateExpense(@RequestBody SaveExpenseDto saveExpenseDto, @PathVariable Integer expenseId) {

        var expense = expenseService.updateExpense(saveExpenseDto, expenseId);

        if (Objects.isNull(expense)) {
            return new ResponseDto("error", "Expense not found with id: " + expenseId, 404);
        }

        return new ResponseDto("ok", expense, 200);
    }


}
