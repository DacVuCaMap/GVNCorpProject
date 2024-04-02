package com.GVNCop.app.Controller;

import com.GVNCop.app.Entity.Account;
import com.GVNCop.app.Entity.ActiveToken;
import com.GVNCop.app.Entity.Token;
import com.GVNCop.app.Repository.TokenRepository;
import com.GVNCop.app.Request.AccountRequest;
import com.GVNCop.app.Request.ActiveRequest;
import com.GVNCop.app.Request.LoginRequest;
import com.GVNCop.app.Response.AccountResponse;
import com.GVNCop.app.SendMail.EmailDetails;
import com.GVNCop.app.SendMail.EmailService;
import com.GVNCop.app.Service.AccountService;
import com.GVNCop.app.Service.ActiveTokenService;
import com.GVNCop.app.Service.AuthenticaionService;
import com.GVNCop.app.Service.TokenService;
import com.GVNCop.app.Validators.ObjectValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticaionService authenticaionService;
    private final ObjectValidator<AccountRequest> objectValidator;
    private final AccountService accountService;
    private final EmailService emailService;
    private final ActiveTokenService activeTokenService;
    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        AccountResponse accountResponse = authenticaionService.loginAccount(loginRequest);
        if(!accountResponse.getStatus().equals("0")){
            return ResponseEntity.badRequest().body(accountResponse.getMess());
        }
        Cookie cookie = new Cookie("jwt",accountResponse.getAccessToken());
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(accountResponse);
    }

    //register
    @PostMapping("/register")
    public ResponseEntity<?> registerPost(@RequestBody AccountRequest accReq, HttpServletRequest request){

        //search unique email
        Account account = accountService.getAccByEmail(accReq.getEmail());
        Long id = null;
//        System.out.println(account);
        if (account!=null && account.isActive()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else if (account!=null && !account.isActive()) {
            id=account.getId();
        }
        //validation
        var violation = objectValidator.validate(accReq);
        if (!violation.isEmpty()){
//            System.out.println(violation);
            return ResponseEntity.badRequest().body(String.join(" | ",violation));
        }
        String reqUrl = request.getRequestURL().toString();
        String domain="";
        try {
            URI uri = new URI(reqUrl);
            URL url = uri.toURL();
            domain = url.getProtocol() + "://" + url.getAuthority();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok(authenticaionService.RegisterAccount(accReq,id,domain));
    }
    @PostMapping("/get-active/{activeString}")
    public ResponseEntity<?> getActive(@PathVariable String activeString){
        System.out.println("get active + activeString: "+activeString);
        ActiveToken activeToken = activeTokenService.getActiveTokenByStr(activeString);
        if (activeToken==null){
            return ResponseEntity.badRequest().body("khong co trong db");
        }
        activeTokenService.deleteActiveToken(activeToken);
        return ResponseEntity.ok().body(activeToken.getAccount().getEmail());
    }
}
