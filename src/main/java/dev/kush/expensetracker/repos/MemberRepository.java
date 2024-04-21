package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query(value = """
            select m from Member m where m.email = :email
            """)
    Optional<Member> findMemberByEmail(String email);

    @Query(value = """
            select (count(m) > 0) from Member m
            where m.email = :email
            """)
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = """
            update Member m set m.isEnabled = true where
            m.email = :email
            """)
    int updateIsEnabledByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = """
            update Member m set m.isEnabled = true where
            m.memberId = :memberID
            """)
    int updateIsEnabledByMemberId(Integer memberID);

    @Transactional
    @Modifying
    @Query(value = """
            delete from Member m where m.memberId = :memberId
            """)
    int deleteMemberByMemberId(Integer memberId);

    @Transactional
    @Modifying
    @Query(value = """
            delete from Member m where m.email = :email
            """)
    int deleteMemberByEmail(String email);
}