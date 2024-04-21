package dev.kush.expensetracker.services.impl;

import dev.kush.expensetracker.models.entities.ConformationToken;
import dev.kush.expensetracker.models.entities.Member;
import dev.kush.expensetracker.repos.ConformationTokenRepository;
import dev.kush.expensetracker.services.api.ConformationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConformationTokenServiceImpl implements ConformationTokenService {

    private final ConformationTokenRepository conformationTokenRepository;

    @Override
    public String saveAndGenerateToken(Member member) {
        String token = UUID.randomUUID().toString();

        ConformationToken conformationToken = new ConformationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),
                null, member
        );

        return conformationTokenRepository.save(conformationToken).getToken();
    }

    @Override
    public Optional<ConformationToken> findConformationTokenByToken(String token) {
        return conformationTokenRepository.findConformationTokenByToken(token);
    }

    @Override
    public int setConformedAt(String token) {
        return conformationTokenRepository.updateConformationTokenAtByToken(LocalDateTime.now(),token);
    }
}
