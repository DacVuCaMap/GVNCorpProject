package com.GVNCop.app.Service;

import com.GVNCop.app.Repository.TokenRepository;
import com.GVNCop.app.Response.MessageResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MessageResponse messageResponse = new MessageResponse();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        String token;
        if (cookies!=null){
            cookie = Arrays.stream(cookies)
                    .filter(x->x.getName().equals("jwt")).findFirst().orElse(null);
        }
        if (cookies == null || cookie == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            messageResponse.setMessage(response,"JWT cookies khong tim thay");
        }
        else{
            token=cookie.getValue();
            var tokenValid =tokenRepository.findTokenByTokenString(token).orElse(null);
            if (tokenValid==null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                messageResponse.setMessage(response,"Token khong tim thay");
            }
            else{
                tokenValid.setRevoked(true);
                tokenValid.setExpiration(true);
                tokenRepository.save(tokenValid);
                SecurityContextHolder.clearContext();
                cookie=new Cookie("jwt",null);
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.setStatus(HttpServletResponse.SC_OK);
                messageResponse.setMessage(response,"logout success");
            }
        }
    }
}
