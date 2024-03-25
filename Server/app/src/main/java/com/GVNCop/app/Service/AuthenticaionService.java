package com.GVNCop.app.Service;

import com.GVNCop.app.Entity.Account;
import com.GVNCop.app.Entity.Role;
import com.GVNCop.app.Entity.Token;
import com.GVNCop.app.Repository.RoleRepository;
import com.GVNCop.app.Repository.TokenRepository;
import com.GVNCop.app.Request.AccountRequest;
import com.GVNCop.app.Request.LoginRequest;
import com.GVNCop.app.Response.AccountResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticaionService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccountService accountService;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;

    //authentication manager
    private final AuthenticationManager authenticationManager;

    //login
    public AccountResponse loginAccount(LoginRequest loginRequest){
        Account account = accountService.getAccByEmail(loginRequest.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
//        Account account = accountService.getAccByEmail(loginRequest.getEmail());

        var tokenString = jwtService.generateToken(account);
        RevokeAccountToken(account); // thu hoi token tren cac noi khac
        //save account token
        Token saveToken = Token.builder().tokenString(tokenString).account(account).expiration(false).revoked(false).build();
        tokenRepository.save(saveToken);
        return AccountResponse.builder().accessToken(tokenString).name(account.getEmail()).build();
    }

    //Thu Hoi token all acc (khi dang nhap ma tai khoan nay dang co token o may khac se lap tuc het han)
    private void RevokeAccountToken(Account account){
        List<Token> tokens = tokenRepository.findAccTokenExist(account.getId());
        if (!tokens.isEmpty() ){
            tokens.forEach(x->{
                x.setExpiration(true);
                x.setRevoked(true);
            });
            tokenRepository.saveAll(tokens);
        }
    }
    public String RegisterAccount(AccountRequest accReq){
        List<Role> roleList = roleRepository.findByName("user_role");
        //check roleList
        System.out.println(roleList);
        Set<Role> roleSet = roleList.stream().collect(Collectors.toSet());
        //get date dd-mm-yyyy
        LocalDate curDay=LocalDate.now();
        var account = Account.builder().email(accReq.getEmail())
                .password(passwordEncoder.encode(accReq.getPassword()))
                .name(accReq.getName())
                .wallet(0.0)
                .createAt(Date.valueOf(curDay))
                .phoneNumber(accReq.getPhoneNumber()).roleSet(roleSet).build();
        //save into db
        System.out.println("register "+ account);
        accountService.save(account);
        return "dang ky xong "+ account.getName()+" time= "+curDay;
    }
}
