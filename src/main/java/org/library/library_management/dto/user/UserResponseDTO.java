package org.library.library_management.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    String firstName;
    String lastName;
    String email;
    String role;
}
