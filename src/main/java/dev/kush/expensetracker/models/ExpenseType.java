package dev.kush.expensetracker.models;

import dev.kush.expensetracker.repos.ExpenseTypeRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense_types")
public class ExpenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_type_id", nullable = false)
    private Integer expenseTypeId;

    @Column(name = "type_name", nullable = false, length = 100)
    private String typeName;

    public ExpenseType(String typeName) {
        this.typeName = typeName;
    }
}