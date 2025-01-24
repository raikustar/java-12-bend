package com.rainer.veebipood.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class JwtFilter extends BasicAuthenticationFilter {
    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Value("${auth.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getMethod());
        log.info(request.getRequestURL());
        log.info(request.getRequestURI());
        log.info(response.getStatus());

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode((secretKey)));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String id = claims.get("email").toString();
            String admin = claims.get("admin").toString();
            log.info(token);

            List<GrantedAuthority> authorities = new ArrayList<>();

            if (admin.equals("true")) {
                GrantedAuthority authority = new SimpleGrantedAuthority("admin");
                authorities.add(authority);
            }


            Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        super.doFilterInternal(request, response, filterChain);

    }
}
