package dev.kush.expensetracker.controllers;

import dev.kush.expensetracker.constants.ErrorMessageConstants;
import dev.kush.expensetracker.dtos.ResponseDto;
import dev.kush.expensetracker.services.api.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/email")
    public ResponseDto findMemberByEmail(@RequestParam String email) {
        var memberDto = memberService.findMemberByEmail(email);

        if (Objects.isNull(memberDto)) {
            return new ResponseDto(ErrorMessageConstants.ERROR_MESSAGE,
                    "Member not found with email: " + email,
                    HttpStatus.NOT_FOUND.value());
        }

        return new ResponseDto("ok", memberDto, HttpStatus.OK.value());

    }

    @GetMapping("/{memberId}")
    public ResponseDto findMemberByMemberId(@PathVariable Integer memberId) {
        var memberDto = memberService.findMemberByMemberId(memberId);

        if (Objects.isNull(memberDto)) {
            return new ResponseDto(ErrorMessageConstants.ERROR_MESSAGE,
                    "Member not found with id: " + memberId,
                    HttpStatus.NOT_FOUND.value());
        }

        return new ResponseDto("ok", memberDto, HttpStatus.OK.value());
    }

    @DeleteMapping("/email")
    public ResponseDto deleteMemberByEmail(@RequestParam String email) {
        var rowsDeleted = memberService.deleteMemberByEmail(email);

        if (rowsDeleted == 0) {
            return new ResponseDto(ErrorMessageConstants.ERROR_MESSAGE,
                    "Member not found with email: " + email,
                    HttpStatus.NOT_FOUND.value());
        }
        return new ResponseDto("ok",
                "Member deleted successfully with email: " + email, HttpStatus.OK.value());
    }

    @DeleteMapping("/{memberId}")
    public ResponseDto deleteMemberByMemberId(@PathVariable Integer memberId) {
        var rowsDeleted = memberService.deleteMemberByMemberId(memberId);

        if (rowsDeleted == 0) {
            return new ResponseDto(ErrorMessageConstants.ERROR_MESSAGE,
                    "Member not found with id: " + memberId,
                    HttpStatus.NOT_FOUND.value());
        }
        return new ResponseDto(ErrorMessageConstants.OK_MESSAGE,
                "Member deleted successfully with id: " + memberId, HttpStatus.OK.value());
    }

}
