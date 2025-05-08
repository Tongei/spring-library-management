package org.library.library_management.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.library.library_management.exception.InvalidFileException;
import org.library.library_management.exception.ResourceNotFoundException;
import org.library.library_management.models.Book;
import org.library.library_management.repositories.BookRepository;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.BookService;
import org.library.library_management.services.CategoryService;
import org.library.library_management.services.util.PageUtil;
import org.library.library_management.specification.book.BookFilter;
import org.library.library_management.specification.book.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final Path uploadPath = Paths.get("uploads/covers");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(uploadPath);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getAll(String title, Long cateId, Long authorId, Integer page, Integer size) {
        BookFilter filter = new BookFilter();
        filter.setTitle(title);
        filter.setCategoryId(cateId);
        filter.setAuthorId(authorId);
        Specification<Book> spec = new BookSpecification(filter);
        Pageable pageable = PageUtil.getPageable(page, size);
        return bookRepository.findAll(spec, pageable);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Book"));
    }

    @Override
    public void deleteById(Long id) {
        Book book = getById(id);
        if (book.getImageName() != null && !book.getImageName().equals("no-cover.png")) {
            try{
                Path oldImagePath = uploadPath.resolve(book.getImageName());
                Files.deleteIfExists(oldImagePath);
            }catch (IOException e){
                throw new RuntimeException("Failed to delete image", e);
            }
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void update(Book book) {
        getById(book.getId());
        bookRepository.save(book); // Save updated book
    }


    @Override
    public Book uploadBookCover(Long id, MultipartFile file) {
        Book book = getById(id);
        validateImage(file);
        String oldImageName = book.getImageName();
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        try{
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            if (oldImageName != null && !oldImageName.equals("no-cover.png")) {
                Path oldImagePath = uploadPath.resolve(oldImageName);
                Files.deleteIfExists(oldImagePath);
            }
            book.setImageName(fileName);
            book = bookRepository.save(book);
            return book;
        }catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
    @Override
    public Book deleteBookCover(Long id) {
        Book book = getById(id);
        if(book.getImageName() != null && !book.getImageName().equals("no-cover.png")){
            try {
                Path oldImagePath = uploadPath.resolve(book.getImageName());
                Files.deleteIfExists(oldImagePath);
            }catch (IOException e){
                throw new RuntimeException("Failed to delete image", e);
            }
            book.setImageName(null);
            return bookRepository.save(book);
        }
        return null;
    }
    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) throw new InvalidFileException("File cannot be empty");
        if(file.getSize() > 5 * 1024 * 1024) throw new MaxUploadSizeExceededException(5 * 1024 * 102);
        if (!List.of("image/jpeg", "image/png", "image/jpg").contains(file.getContentType())) throw new InvalidFileException("Only JPEG/PNG allowed");
    }
}
