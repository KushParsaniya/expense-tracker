package dev.kush.expensetracker.dtos.impl;

import dev.kush.expensetracker.dtos.ExpenseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDtoImpl implements ExpenseDto {
    private Integer expenseId;
    private BigDecimal amount;
    private String description;
    private LocalDate createdDate;
    private String expenseTypeName;
    private LocalTime createdTime;

}