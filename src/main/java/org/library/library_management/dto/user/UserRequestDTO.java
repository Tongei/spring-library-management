package org.library.library_management.dto.user;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
}
