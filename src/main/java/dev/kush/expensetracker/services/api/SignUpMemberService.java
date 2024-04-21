package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.SignUpDto;

public interface SignUpMemberService {

    String signUp(SignUpDto signUpDto);

    int enableAccountByEmail(String email);

    int enableAccountByMemberId(Integer memberId);

    String conformToken(String token);

}
