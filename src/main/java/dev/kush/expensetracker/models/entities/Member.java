package dev.kush.expensetracker.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone", length = 12)
    private String phone;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ColumnDefault("1")
    @JoinColumn(name = "role_id")
    private Role role;

    @ColumnDefault("false")
    @Column(name = "is_locked")
    private Boolean isLocked;

    @ColumnDefault("false")
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "member")
    private List<Expense> expenses;

    public Member(String name, String email, String password, String phone, LocalDate createdAt, Role role, Boolean isLocked, Boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = createdAt;
        this.role = role;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
    }
}