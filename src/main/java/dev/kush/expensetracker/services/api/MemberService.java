package dev.kush.expensetracker.services.api;

import dev.kush.expensetracker.dtos.api.MemberDto;

public interface MemberService {

    MemberDto findMemberByMemberId(Integer memberId);

    MemberDto findMemberByEmail(String email);

    int deleteMemberByMemberId(Integer memberId);

    int deleteMemberByEmail(String email);

    // TODO: update member
}
