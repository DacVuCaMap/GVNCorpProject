package com.GVNCop.app.Config;

import com.GVNCop.app.Repository.TokenRepository;
import com.GVNCop.app.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("api/auth")){
            filterChain.doFilter(request,response);
            return;
        }
        Cookie[] cookies =request.getCookies();
        Cookie cookie=null;
        if (cookies != null){
            cookie = Arrays.stream(cookies)
                    .filter(x->x.getName().equals("jwt")).findFirst().orElse(null);
        }
        if (cookies==null || cookie==null){
            filterChain.doFilter(request,response);
            return;
        }
        String token = cookie.getValue();
        String accMail = jwtService.extraName(token);
        if (accMail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(accMail);
            var tokenvalid =tokenRepository.findTokenByTokenString(token).map(x->!x.isExpiration() && !x.isRevoked())
                    .orElse(false);
            if (jwtService.tokenValid(token,userDetails) && tokenvalid){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
