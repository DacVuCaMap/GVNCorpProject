package com.GVNCop.app.Service;

import com.GVNCop.app.Entity.Account;
import com.GVNCop.app.Entity.ActiveToken;
import com.GVNCop.app.Repository.ActiveTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

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
    public void deleteActiveToken(ActiveToken activeToken){
        activeTokenRepository.delete(activeToken);
    }
    public void CheckAndSave(ActiveToken activeToken){
        // check exist ? by account id and todo
        ActiveToken activeTokenExist = findByAccAndTodo(activeToken.getAccount().getId(),activeToken.getTodo());
        if (activeTokenExist != null){
            activeToken.setId(activeTokenExist.getId());
        }
        System.out.println(activeToken);
        save(activeToken);
    }
    public ActiveToken findByAccAndTodo(Long accId,String todo){
        System.out.println("accid: "+accId.toString() + " todo: "+todo);
        Optional<ActiveToken> account = activeTokenRepository.findByAccAndTodo(accId,todo);
        return account.orElse(null);
    }
    public void save(ActiveToken activeToken){
        activeTokenRepository.save(activeToken);
    }
}
