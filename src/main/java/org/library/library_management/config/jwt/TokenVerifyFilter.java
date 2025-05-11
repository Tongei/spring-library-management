package org.library.library_management.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.library.library_management.exception.ApiException;
import org.library.library_management.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TokenVerifyFilter extends OncePerRequestFilter {
    private static final List<String> PUBLIC_PATHS = List.of(
        "/api/v1/auth/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader("Authorization");
        String path = request.getRequestURI();

        // If path is public, skip token verification
        if (PUBLIC_PATHS.contains(path) && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            try {
                String token = headerToken.replace("Bearer ", "");
                String secretKey = "verySecretKeyThatIsVeryLongForHS256ToWorkProperly!";
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                                        .build()
                                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
                Set<SimpleGrantedAuthority> authority = authorities.stream()
                        .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                        .collect(Collectors.toSet());
                String requiredRole = "ROLE_";
                boolean hasRequiredRole = authority.stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().startsWith(requiredRole));
                if (!hasRequiredRole) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Access Denied: Insufficient Permissions\"}");
                    response.getWriter().flush();
                    return;
                }
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authority);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("application/json");
//                response.getWriter().write("{\"error\": \"Invalid Authorization token\", \"exception\": \"" + e.getMessage() + "\"}");
//                response.getWriter().flush();
                throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid Authorization token");
            }
            filterChain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid or missing Authorization token\"}");
        response.getWriter().flush();
    }
}
