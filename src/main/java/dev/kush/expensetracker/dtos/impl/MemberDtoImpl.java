package dev.kush.expensetracker.dtos.impl;

import dev.kush.expensetracker.dtos.api.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDtoImpl implements MemberDto {
    private Integer memberId;
    private String name;
    private String email;
    private String phone;
}
