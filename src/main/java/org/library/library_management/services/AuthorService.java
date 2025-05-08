package org.library.library_management.services;

import org.library.library_management.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AuthorService {
    public Author saveAuthor(Author author);
    public Author getAuthorById(long id);
    public Page<Author> getAllAuthors(String name, Integer page, Integer size);
    public void deleteAuthor(long id);
    public void updateAuthor(Author author);
}
