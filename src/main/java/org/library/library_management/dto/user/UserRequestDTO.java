package org.library.library_management.dto.user;

import lombok.Data;
import org.library.library_management.models.Role;

import java.util.Set;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Set<Long> roleIds;
}
