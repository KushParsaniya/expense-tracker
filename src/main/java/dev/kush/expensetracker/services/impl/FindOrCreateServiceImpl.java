package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.models.ExpenseType;
import dev.kush.expensetracker.models.Role;
import dev.kush.expensetracker.repos.ExpenseTypeRepository;
import dev.kush.expensetracker.services.FindOrCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrCreateServiceImpl implements FindOrCreateService {

    private final ExpenseTypeRepository expenseTypeRepository;

    @Override
    public ExpenseType findOrCreateExpenseType(String typeName) {
        var expenseTypeOptional = expenseTypeRepository.findByTypeName(typeName);

        return expenseTypeOptional.orElseGet(() -> expenseTypeRepository.save(new ExpenseType(typeName)));
    }

    @Override
    public Role findOrCreateRole(String roleName) {
        return null;
    }
}
