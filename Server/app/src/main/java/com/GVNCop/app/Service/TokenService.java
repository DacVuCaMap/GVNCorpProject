package com.GVNCop.app.Service;

import com.GVNCop.app.Entity.Token;
import com.GVNCop.app.Repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    public void saveToken(Token token){
        tokenRepository.save(token);
    }
}
