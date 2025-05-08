package org.library.library_management.dto.login;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
