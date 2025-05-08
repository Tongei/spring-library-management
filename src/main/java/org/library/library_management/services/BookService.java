package org.library.library_management.services;

import org.library.library_management.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    public Book save(Book book);
    public Page<Book> getAll(String title,Long cateId, Long authorId, Integer page, Integer size);
    public Book getById(Long id);
    public void deleteById(Long id) throws IOException;
    public void update(Book book);
    public Book uploadBookCover(Long id, MultipartFile file);
    public Book deleteBookCover(Long id);

}
