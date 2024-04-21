package dev.kush.expensetracker.cache;

import dev.kush.expensetracker.models.entities.ExpenseType;
import dev.kush.expensetracker.repos.ExpenseTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExpenseTypeCache {

    @Getter
    private Map<String, ExpenseType> expenseTypeMap;

    private final ExpenseTypeRepository expenseTypeRepository;

    private final EntityManager entityManager;

    @PostConstruct
    public void init() {
        // Initialize the cache
        expenseTypeMap = new HashMap<>();
        expenseTypeRepository.findAll()
                .forEach(this::addToCache);

    }

    protected void addToCache(ExpenseType expenseType) {
        expenseTypeMap.put(expenseType.getTypeName().toLowerCase(), expenseType);
    }

}
