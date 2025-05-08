package org.library.library_management.config.security;

import lombok.RequiredArgsConstructor;
import org.library.library_management.exception.ApiException;
import org.library.library_management.models.Role;
import org.library.library_management.models.User;
import org.library.library_management.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<AuthUser> loadUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<AuthUser> loadUserByEmail(String email) {
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Invalid Email or Password"));
        AuthUser authUser = AuthUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                 .authorities(getGrantedAuthorities(user.getRoles()))
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                        .build();
        return Optional.ofNullable(authUser);
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        Set<SimpleGrantedAuthority> collect = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())).collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .flatMap(this::toStream)
                .collect(Collectors.toSet());
        authorities.addAll(collect);
        return authorities;
    }

    private Stream<SimpleGrantedAuthority> toStream(Role role) {
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));

    }
}
