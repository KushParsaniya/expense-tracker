package dev.kush.expensetracker.services.api;


import dev.kush.expensetracker.models.entities.ConformationToken;
import dev.kush.expensetracker.models.entities.Member;

import java.util.Optional;

public interface ConformationTokenService {

    String saveAndGenerateToken(Member member);

    Optional<ConformationToken> findConformationTokenByToken(String token);

    int setConformedAt(String token);
}
