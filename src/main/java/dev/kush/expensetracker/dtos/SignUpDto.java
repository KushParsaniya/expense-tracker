package dev.kush.expensetracker.dtos;

import java.io.Serializable;

public record SignUpDto(String name, String email, String password, String phone) implements Serializable {
}