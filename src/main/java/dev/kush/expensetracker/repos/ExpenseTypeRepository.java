package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {


    @Query(value = """
                        SELECT et
                        FROM ExpenseType et
                        WHERE lower(et.typeName) = lower(:typeName)
            """)
    Optional<ExpenseType> findByTypeName(String typeName);
}