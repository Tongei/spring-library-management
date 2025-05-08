package org.library.library_management.specification.book;

import lombok.Data;

@Data
public class BookFilter {
    private String title;
    private Long authorId;
    private Long categoryId;
}
