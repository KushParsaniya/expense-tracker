package dev.kush.expensetracker.repos;

import dev.kush.expensetracker.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}