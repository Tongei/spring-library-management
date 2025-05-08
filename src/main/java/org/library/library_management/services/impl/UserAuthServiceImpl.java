package org.library.library_management.services.impl;

import lombok.RequiredArgsConstructor;
import org.library.library_management.models.User;
import org.library.library_management.repositories.UserRepository;
import org.library.library_management.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }
}
