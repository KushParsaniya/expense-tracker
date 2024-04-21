package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.cache.ExpenseTypeCache;
import dev.kush.expensetracker.models.entities.ExpenseType;
import dev.kush.expensetracker.models.entities.Role;
import dev.kush.expensetracker.repos.ExpenseTypeRepository;
import dev.kush.expensetracker.repos.RoleRepository;
import dev.kush.expensetracker.services.api.FindOrCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrCreateServiceImpl implements FindOrCreateService {

    private final ExpenseTypeRepository expenseTypeRepository;

    private final RoleRepository roleRepository;

    private final ExpenseTypeCache expenseTypeCache;


    @Override
    public ExpenseType findOrCreateExpenseType(String typeName) {

        // TODO: solve this
//        if (expenseTypeCache.getExpenseTypeMap().containsKey(typeName.toLowerCase())) {
//            return expenseTypeCache.getExpenseTypeMap().get(typeName.toLowerCase());
//        }

        return expenseTypeRepository.findByTypeName(typeName)
                .orElseGet(() -> expenseTypeRepository.save(new ExpenseType(typeName)));
    }

    @Override
    public Role findOrCreateRole(String roleName) {
        return roleRepository.findRoleByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
