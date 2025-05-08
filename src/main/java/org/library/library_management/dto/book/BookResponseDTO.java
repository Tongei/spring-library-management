package org.library.library_management.dto.book;

import lombok.Data;
import org.library.library_management.models.Author;
import org.library.library_management.models.Category;

@Data
public class BookResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String imageName;
    private Author author;
    private Category category;
}
