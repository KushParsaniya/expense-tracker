package dev.kush.expensetracker.controllers;

import dev.kush.expensetracker.dtos.ResponseDto;
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

    @PostMapping
    public ResponseDto saveMember(@RequestBody SignUpDto signUpDto) {
        var memberDto = signUpMemberService.signUp(signUpDto);

        if (Objects.isNull(memberDto)) {
            return new ResponseDto("error",
                    "Member already exists with email: " + signUpDto.email(),
                    HttpStatus.CONFLICT.value());
        }

        return new ResponseDto("ok", memberDto, HttpStatus.CREATED.value());
    }

    @GetMapping("/conform")
    public String conformToken(@RequestParam String token) {
        return signUpMemberService.conformToken(token);
    }

}
