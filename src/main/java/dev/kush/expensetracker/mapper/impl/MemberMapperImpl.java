package dev.kush.expensetracker.mapper.impl;

import dev.kush.expensetracker.dtos.SignUpDto;
import dev.kush.expensetracker.dtos.api.MemberDto;
import dev.kush.expensetracker.dtos.impl.MemberDtoImpl;
import dev.kush.expensetracker.mapper.MemberMapper;
import dev.kush.expensetracker.models.entities.Member;
import dev.kush.expensetracker.services.api.FindOrCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MemberMapperImpl implements MemberMapper {

    private final FindOrCreateService findOrCreateService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member mapSignUpDtoToMember(SignUpDto signUpDto) {
        return new Member(
                signUpDto.name(),
                signUpDto.email(),
                passwordEncoder.encode(signUpDto.password()),
                signUpDto.phone(),
                LocalDate.now(),
                findOrCreateService.findOrCreateRole("ROLE_USER"),
                false,
                false
        );
    }

    @Override
    public MemberDto mapMemberToMemberDto(Member member) {
        return new MemberDtoImpl(member.getMemberId(), member.getName(), member.getEmail(), member.getPhone());
    }
}
