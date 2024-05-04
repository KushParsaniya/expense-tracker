package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.ResponseDto;
import dev.kush.expensetracker.dtos.SignUpDto;

public interface SignUpMemberService {

    String signUp(SignUpDto signUpDto);

    int enableAccountByEmail(String email);

    int enableAccountByMemberId(Integer memberId);

    String conformToken(String token);

    ResponseDto signIn(String base64Encode);
}
