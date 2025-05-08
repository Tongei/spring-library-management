package org.library.library_management.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.SqlTypes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String secretKey = "verySecretKeyThatIsVeryLongForHS256ToWorkProperly!";
        String token = Jwts.builder()
                        .setSubject(authResult.getName())
                        .claim("authorities", authResult.getAuthorities())
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                        .setIssuer("ADMIN")
                        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .compact();
        // response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write token to response body
        Map<String, String> responseBody = Map.of("message", "Login Successful","token", token);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> responseBody = Map.of("message", "Login Failed");
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}
