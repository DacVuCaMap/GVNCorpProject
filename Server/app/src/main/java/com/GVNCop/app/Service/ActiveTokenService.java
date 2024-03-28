package com.GVNCop.app.Service;

import com.GVNCop.app.Entity.ActiveToken;
import com.GVNCop.app.Repository.ActiveTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ActiveTokenService {
    private final ActiveTokenRepository activeTokenRepository;

    public String generateActiveToken(){
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        String tokenString = Base64.getUrlEncoder().encodeToString(randomBytes);
        return tokenString;
    }

    public ActiveToken getActiveTokenByStr(String str){

        return activeTokenRepository.findByName(str).orElse(null);
    }

}
