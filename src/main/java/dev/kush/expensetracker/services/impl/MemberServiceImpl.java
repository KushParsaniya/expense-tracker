package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.dtos.MemberDto;
import dev.kush.expensetracker.mapper.MemberMapper;
import dev.kush.expensetracker.repos.MemberRepository;
import dev.kush.expensetracker.services.api.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    @Override
    public MemberDto findMemberByMemberId(Integer memberId) {
        try {
            var member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("member not found with id: " + memberId));

            return memberMapper.mapMemberToMemberDto(member);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public MemberDto findMemberByEmail(String email) {
        try {
            var member = memberRepository.findMemberByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("member not found with email: " + email));

            return memberMapper.mapMemberToMemberDto(member);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    @Override
    public int deleteMemberByMemberId(Integer memberId) {
        return memberRepository.deleteMemberByMemberId(memberId);
    }

    @Override
    public int deleteMemberByEmail(String email) {
        return memberRepository.deleteMemberByEmail(email);
    }
}
