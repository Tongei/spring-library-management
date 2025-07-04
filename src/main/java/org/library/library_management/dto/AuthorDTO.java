package org.library.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorDTO {
    @NotBlank(message = "Name is required!")
    private String name;
}