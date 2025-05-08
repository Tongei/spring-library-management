package org.library.library_management.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceFakeImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<AuthUser> loadUserByUsername(String username) {
        List<AuthUser> users = List.of(
                new AuthUser(RoleEnum.ADMIN.getGrantedAuthorities(), "theking", passwordEncoder.encode("theking123"), true, true, true, true),
                new AuthUser(RoleEnum.USER.getGrantedAuthorities(), "theuser", passwordEncoder.encode("theking123"), true, true, true, true)
        );
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<AuthUser> loadUserByEmail(String email) {
        return Optional.empty();
    }
}
