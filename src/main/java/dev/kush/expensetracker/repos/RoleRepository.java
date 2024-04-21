package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = """
            select r from Role r where r.roleName = :roleName
            """)
    Optional<Role> findRoleByRoleName(String roleName);
}