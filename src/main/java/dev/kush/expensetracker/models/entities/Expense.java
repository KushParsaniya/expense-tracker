package dev.kush.expensetracker.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", nullable = false)
    private Integer expenseId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "created_date")
    private LocalDate createdDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_time")
    private LocalTime createdTime;

    @ManyToOne(cascade = {PERSIST,MERGE})
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @ManyToOne(cascade = {PERSIST,MERGE})
    @JoinColumn(name = "member_id")
    private Member member;

    public Expense(BigDecimal amount, String description, LocalDate createdDate, LocalTime createdTime, ExpenseType expenseType, Member member) {
        this.amount = amount;
        this.description = description;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.expenseType = expenseType;
        this.member = member;
    }
}