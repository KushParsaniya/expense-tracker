package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}