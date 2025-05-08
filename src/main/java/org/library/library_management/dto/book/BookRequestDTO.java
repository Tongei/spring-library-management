package org.library.library_management.dto.book;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequestDTO {
    @NotBlank(message = "Title is require!")
    private String title;
    @NotBlank(message = "Description is require!")
    private String description;
    private String imageName;
    @NotNull(message = "Author is required!")
    private Long authorId;
    @NotNull(message = "Category is required!")
    private Long categoryId;
}
