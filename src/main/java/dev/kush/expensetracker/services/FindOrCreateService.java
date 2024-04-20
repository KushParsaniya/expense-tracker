package dev.kush.expensetracker.services;

import dev.kush.expensetracker.models.ExpenseType;
import dev.kush.expensetracker.models.Role;

public interface FindOrCreateService {
    ExpenseType findOrCreateExpenseType(String typeName);

    Role findOrCreateRole(String roleName);
}
