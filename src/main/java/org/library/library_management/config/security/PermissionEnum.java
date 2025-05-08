package org.library.library_management.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
    BOOKS_READ("books:read"),
    BOOKS_WRITE("books:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write");

    private final String description;
}
