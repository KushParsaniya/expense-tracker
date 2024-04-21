package dev.kush.expensetracker.mapper;


import dev.kush.expensetracker.dtos.MemberDto;
import dev.kush.expensetracker.dtos.SignUpDto;
import dev.kush.expensetracker.models.entities.Member;

public interface MemberMapper {

    Member mapSignUpDtoToMember(SignUpDto signUpDto);

    MemberDto mapMemberToMemberDto(Member member);

}
