package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.models.entities.ExpenseType;
import dev.kush.expensetracker.models.entities.Role;

public interface FindOrCreateService {
    ExpenseType findOrCreateExpenseType(String typeName);

    Role findOrCreateRole(String roleName);
}
