package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.dtos.ExpenseDto;
import dev.kush.expensetracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value = """
            select e.expenseId as expenseId, e.amount as amount, e.description as description,
            e.createdDate as createdDate, e.createdTime as createdTime, et.typeName as expenseTypeName
            from Expense e join ExpenseType et on e.expenseType = et
            where e.expenseId = :expenseId
            """)
    Optional<ExpenseDto> findExpenseDtoById(Integer expenseId);

    @Transactional
    @Modifying
    @Query("delete from Expense e where e.expenseId = :expenseId")
    int deleteByExpenseId(Integer expenseId);


    @Query(value = """
            select e.expenseId as expenseId, e.amount as amount, e.description as description,
            e.createdDate as createdDate, e.createdTime as createdTime, et.typeName as expenseTypeName
            from Expense e join ExpenseType et on e.expenseType = et
            where e.member.memberId = :memberId
            """)
    List<ExpenseDto> findAllExpensesByMemberId(Integer memberId);


    @Query(value = """
            select e.expenseId as expenseId, e.amount as amount, e.description as description,
            e.createdDate as createdDate, e.createdTime as createdTime, et.typeName as expenseTypeName
            from Expense e join ExpenseType et on e.expenseType = et join Member m on e.member = m
            where m.email = :email
            """)
    List<ExpenseDto> findAllExpensesByMemberEmail(String email);

    @Query(value = """
            select e.expenseId as expenseId, e.amount as amount, e.description as description,
            e.createdDate as createdDate, e.createdTime as createdTime, et.typeName as expenseTypeName
            from Expense e join ExpenseType et on e.expenseType = et
            where e.member.memberId = :memberId
            and e.createdDate between :startDate and :endDate""")
    List<ExpenseDto> findAllExpensesByMemberIdAndDateRange(Integer memberId, LocalDate startDate, LocalDate endDate);

    @Query(value = """
            select e.expenseId as expenseId, e.amount as amount, e.description as description,
            e.createdDate as createdDate, e.createdTime as createdTime, et.typeName as expenseTypeName
            from Expense e join ExpenseType et on e.expenseType = et join Member m on e.member = m
            where m.email = :email
            and e.createdDate between :startDate and :endDate
            """)
    List<ExpenseDto> findAllExpensesByMemberEmailAndDateRange(String email, LocalDate startDate, LocalDate endDate);

}