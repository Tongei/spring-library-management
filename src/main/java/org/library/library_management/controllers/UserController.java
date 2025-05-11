package org.library.library_management.controllers;

import org.library.library_management.dto.user.UserRequestDTO;
import org.library.library_management.mapper.UserMapper;
import org.library.library_management.models.User;
import org.library.library_management.payload.ApiResponse;
import org.library.library_management.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.version}")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;


    @PostMapping("/auth/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("USer DTO" + userRequestDTO);
        User user = UserMapper.INSTANCE.toUser(userRequestDTO);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userAuthService.saveUser(user);
        if(result == null){
            return ResponseEntity.badRequest().body(new ApiResponse("Email already in used.", null));
        }
        ApiResponse response = new ApiResponse("Create success", UserMapper.INSTANCE.toUserResponseDTO(result));
        return ResponseEntity.ok().body(response);
    }
}
