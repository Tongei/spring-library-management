package org.library.library_management.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.library.library_management.config.AppProperties;
import org.library.library_management.dto.PageDTO;
import org.library.library_management.dto.book.BookRequestDTO;
import org.library.library_management.dto.book.BookResponseDTO;
import org.library.library_management.mapper.BookMapper;
import org.library.library_management.models.Book;
import org.library.library_management.payload.ApiResponse;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.BookService;
import org.library.library_management.services.CategoryService;
import org.library.library_management.services.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${app.version}/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookMapper bookMapper;
    private final AppProperties appProperties;


    @PreAuthorize("hasAuthority('books:write')")
    @PostMapping
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book book = bookMapper.toBook(bookRequestDTO);
        categoryService.getById(bookRequestDTO.getCategoryId());
        authorService.getAuthorById(bookRequestDTO.getAuthorId());
        BookResponseDTO bookResponseDTO = bookMapper.toBookResponseDTO(bookService.save(book));
        if(bookResponseDTO.getImageName() == null || bookResponseDTO.getImageName().isEmpty()) {
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + appProperties.getDefaultCover());
        } else{
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + bookResponseDTO.getImageName());
        }
        ApiResponse response = new ApiResponse("Create success", bookResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:read')")
    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(name = PageUtil.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = PageUtil.PAGE_SIZE, required = false) Integer size
    ) {
        Page<Book> books = bookService.getAll(title, categoryId, authorId, page, size);
        books.getContent().forEach(book -> {
            if(book.getImageName() == null || book.getImageName().isEmpty()) {
                book.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + appProperties.getDefaultCover());
            }else{
                book.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + book.getImageName());
            }
        });
        PageDTO pageDTO = new PageDTO(books);
        ApiResponse response = new ApiResponse("Get all books", pageDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:read')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        BookResponseDTO bookResponseDTO = bookMapper.toBookResponseDTO(bookService.getById(id));
        if(bookResponseDTO.getImageName() == null || bookResponseDTO.getImageName().isEmpty()) {
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + appProperties.getDefaultCover());
        } else{
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + bookResponseDTO.getImageName());
        }
        ApiResponse response = new ApiResponse("Get one success", bookResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable long id) throws IOException {
        bookService.deleteById(id);
        Map<String, Long> data = Map.of("id", id);
        ApiResponse response = new ApiResponse("Delete success", data);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:put')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book book = bookMapper.toBook(bookRequestDTO);
        book.setId(id);
        bookService.update(book);
        ApiResponse response = new ApiResponse("Update success", bookMapper.toBookResponseDTO(book));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:write')")
    @PutMapping("/{id}/cover")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("cover") MultipartFile file) {
        BookResponseDTO bookResponseDTO = bookMapper.toBookResponseDTO(bookService.uploadBookCover(id, file));
        bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + bookResponseDTO.getImageName());
        ApiResponse response = new ApiResponse("Upload cover success", bookResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('books:write')")
    @DeleteMapping("{id}/cover")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        Book book = bookService.deleteBookCover(id);
        if(book == null) {
            ApiResponse response = new ApiResponse("Default cover couldn't delete", null);
            return ResponseEntity.ok(response);
        }
        BookResponseDTO bookResponseDTO = bookMapper.toBookResponseDTO(book);
        if(bookResponseDTO.getImageName() == null || bookResponseDTO.getImageName().isEmpty()) {
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + appProperties.getDefaultCover());
        } else{
            bookResponseDTO.setImageName(appProperties.getBaseUrl() + appProperties.getResponseCoverPath() + bookResponseDTO.getImageName());
        }
        ApiResponse response = new ApiResponse("Delete cover success", bookResponseDTO);
        return ResponseEntity.ok(response);
    }

}
