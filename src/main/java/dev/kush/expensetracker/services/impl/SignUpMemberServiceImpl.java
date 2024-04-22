package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.config.security.jwt.JwtService;
import dev.kush.expensetracker.dtos.AuthenticationDto;
import dev.kush.expensetracker.dtos.SignUpDto;
import dev.kush.expensetracker.mapper.MemberMapper;
import dev.kush.expensetracker.models.EmailDetails;
import dev.kush.expensetracker.models.entities.ConformationToken;
import dev.kush.expensetracker.models.entities.Member;
import dev.kush.expensetracker.repos.MemberRepository;
import dev.kush.expensetracker.services.api.Base64Service;
import dev.kush.expensetracker.services.api.ConformationTokenService;
import dev.kush.expensetracker.services.api.MailService;
import dev.kush.expensetracker.services.api.SignUpMemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpMemberServiceImpl implements SignUpMemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    private final ConformationTokenService conformationTokenService;

    private final MailService mailService;

    private final Base64Service base64Service;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value("${mail.url}")
    private String mailUrl;

    @Override
    public String signUp(SignUpDto signUpDto) {
        try {
            if (memberRepository.existsByEmail(signUpDto.email())) {
                throw new IllegalArgumentException("member already exists with email: " + signUpDto.email());
            }

            var member = memberRepository.save(
                    memberMapper.mapSignUpDtoToMember(signUpDto)
            );

            var token = conformationTokenService.saveAndGenerateToken(member);

            String conformationLink = mailUrl + "/api/v1/members/conform?token=" + token;

            sendConformationEmail(member, conformationLink);

            return "successfully created account check your mail for conformation.";

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private void sendConformationEmail(Member member, String conformationLink) throws Exception {
        mailService.sendEmail(new EmailDetails(
                member.getEmail(),
                String.format("""
                        <p>Hello %s your verification link is here click on the link to verify your email</p>
                        <a href=%s>Verify now</a>
                        """, member.getName(), conformationLink),
                "Expense-Tracker Verification mail"));
    }

    @Override
    public int enableAccountByEmail(String email) {
        return memberRepository.updateIsEnabledByEmail(email);
    }

    @Override
    public int enableAccountByMemberId(Integer memberId) {
        return memberRepository.updateIsEnabledByMemberId(memberId);
    }

    @Override
    public String conformToken(String token) {
        ConformationToken conformationToken = conformationTokenService.findConformationTokenByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("token does not exist."));

        if (conformationToken.getConfirmedAt() != null) {
            return "token already confirmed";
        }

        if (conformationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            return "token expired";
        }

        int rowsUpdated = conformationTokenService.setConformedAt(token);

        if (rowsUpdated == 0) {
            return "error while updating token.";
        }
        rowsUpdated = enableAccountByMemberId(conformationToken.getMember().getMemberId());
        if (rowsUpdated == 0) {
            return "error while updating member.";
        }
        return "successfully verified.";
    }

    @Override
    public String signIn(String base64Encode) {
        try {
            AuthenticationDto authenticationDto = base64Service.decode(base64Encode);
            Authentication authenticatedToken = authenticateUser(authenticationDto);
            return generateToken(authenticatedToken);
        } catch (AuthenticationException e) {
            log.error("Authentication failed : {}", e.getMessage());
            return "Authentication failed : " + e.getMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    private Authentication authenticateUser(AuthenticationDto authenticationDto) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password());
        Authentication authenticatedToken = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
        return authenticatedToken;
    }

    private String generateToken(Authentication authenticatedToken) {
        return jwtService.generateToken(authenticatedToken.getPrincipal().toString());
    }
}
