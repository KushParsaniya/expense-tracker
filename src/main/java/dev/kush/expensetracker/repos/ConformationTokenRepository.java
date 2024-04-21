package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.entities.ConformationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConformationTokenRepository extends JpaRepository<ConformationToken, Integer> {

    @Query(value = """
            select ct from ConformationToken ct
            join Member m on ct.member = m
            where ct.token = :token
            """)
    Optional<ConformationToken> findConformationTokenByToken(String token);

    @Transactional
    @Modifying
    @Query(value = """
            update ConformationToken ct set ct.confirmedAt = :conformedAt
            where ct.token = :token
            """)
    int updateConformationTokenAtByToken(LocalDateTime conformedAt, String token);
}