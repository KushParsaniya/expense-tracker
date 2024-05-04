package dev.kush.expensetracker.controllers;

import dev.kush.expensetracker.constants.ErrorMessageConstants;
import dev.kush.expensetracker.dtos.ResponseDto;
import dev.kush.expensetracker.dtos.SignInDto;
import dev.kush.expensetracker.dtos.SignUpDto;
import dev.kush.expensetracker.services.api.SignUpMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class SignUpMemberController {

    private final SignUpMemberService signUpMemberService;

    @PostMapping("/sign-up")
    public ResponseDto saveMember(@RequestBody SignUpDto signUpDto) {
        var memberDto = signUpMemberService.signUp(signUpDto);

        if (Objects.isNull(memberDto)) {
            return new ResponseDto(ErrorMessageConstants.ERROR_MESSAGE,
                    "Member already exists with email: " + signUpDto.email(),
                    HttpStatus.CONFLICT.value());
        }

        return new ResponseDto(ErrorMessageConstants.OK_MESSAGE, memberDto, HttpStatus.CREATED.value());
    }

    @GetMapping("/conform")
    public String conformToken(@RequestParam String token) {
        return signUpMemberService.conformToken(token);
    }

    @PostMapping("/sign-in")
    public ResponseDto signIn(@RequestBody SignInDto signInDto) {
        return signUpMemberService.signIn(signInDto.base64Encoded());

    }


}
