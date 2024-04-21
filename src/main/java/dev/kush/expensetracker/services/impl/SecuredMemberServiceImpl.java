package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.models.SecuredMember;
import dev.kush.expensetracker.repos.MemberRepository;
import dev.kush.expensetracker.services.api.SecuredMemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuredMemberServiceImpl implements SecuredMemberService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecuredMember(memberRepository.findMemberByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("User with email " + username + " not found.")
        ));
    }
}
