package org.library.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotBlank(message = "Name is required!")
    private String name;
}
