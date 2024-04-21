package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.MemberDto;
import dev.kush.expensetracker.dtos.SignUpDto;

public interface MemberService {

    MemberDto findMemberByMemberId(Integer memberId);

    MemberDto findMemberByEmail(String email);

    int deleteMemberByMemberId(Integer memberId);

    int deleteMemberByEmail(String email);

    // TODO: update member
}
