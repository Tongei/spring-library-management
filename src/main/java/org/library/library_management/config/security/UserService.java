package org.library.library_management.config.security;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> loadUserByUsername(String username);
    Optional<AuthUser> loadUserByEmail(String email);
}
