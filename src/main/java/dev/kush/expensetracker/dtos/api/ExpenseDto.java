package dev.kush.expensetracker.dtos.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public interface ExpenseDto {

    String getExpenseTypeName();

    LocalTime getCreatedTime();

    LocalDate getCreatedDate();

    String getDescription();

    BigDecimal getAmount();

    Integer getExpenseId();
}
