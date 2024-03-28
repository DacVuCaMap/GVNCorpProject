package com.GVNCop.app.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {
    @GetMapping("api/auth/csrf")
    public ResponseEntity<?> csrfToken (CsrfToken csrfToken, HttpServletResponse response){
        if (csrfToken!=null){
            Cookie cookie = new Cookie("csrfToken",csrfToken.getToken());
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResponseEntity.ok().body(csrfToken);
    }
}
