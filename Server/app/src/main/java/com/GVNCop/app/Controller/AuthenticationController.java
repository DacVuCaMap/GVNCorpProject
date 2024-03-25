package com.GVNCop.app.Controller;

import com.GVNCop.app.Entity.Account;
import com.GVNCop.app.Entity.Token;
import com.GVNCop.app.Repository.TokenRepository;
import com.GVNCop.app.Request.AccountRequest;
import com.GVNCop.app.Request.LoginRequest;
import com.GVNCop.app.Response.AccountResponse;
import com.GVNCop.app.Service.AccountService;
import com.GVNCop.app.Service.AuthenticaionService;
import com.GVNCop.app.Service.TokenService;
import com.GVNCop.app.Validators.ObjectValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticaionService authenticaionService;
    private final ObjectValidator<AccountRequest> objectValidator;
    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        System.out.println("vao login");
        AccountResponse accountResponse = authenticaionService.loginAccount(loginRequest);
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
    public ResponseEntity<?> registerPost(@RequestBody AccountRequest accReq){
        //validation
        var violation = objectValidator.validate(accReq);
        if (!violation.isEmpty()){
            System.out.println(violation);
            return ResponseEntity.badRequest().body(String.join(" | ",violation));
        }

        System.out.println("register account : "+accReq);
        return ResponseEntity.ok(authenticaionService.RegisterAccount(accReq));
    }
}
