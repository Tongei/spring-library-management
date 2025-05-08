package org.library.library_management.dto.login;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
}
