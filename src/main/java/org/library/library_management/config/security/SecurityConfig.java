package org.library.library_management.config.security;

import org.library.library_management.config.jwt.JwtLoginFilter;
import org.library.library_management.config.jwt.TokenVerifyFilter;
import org.library.library_management.exception.FilterChainExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf().disable()
                .addFilter(new JwtLoginFilter(authenticationManager))
                .addFilterBefore(filterChainExceptionHandler, JwtLoginFilter.class)
                .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
                .authorizeHttpRequests((authz) ->
                        authz
                            .requestMatchers("/", "index.html", "css/**", "js/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                            .anyRequest()
                            .authenticated()
                );
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User.builder()
//                .username("somnang")
//                .password(passwordEncoder.encode("password"))
//                .authorities(RoleEnum.USER.getGrantedAuthorities())
//                .build();
//        UserDetails user2 = User.builder()
//                .username("tongei")
//                .password(passwordEncoder.encode("password"))
//                .authorities(RoleEnum.ADMIN.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}
