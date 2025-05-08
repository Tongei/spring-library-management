package org.library.library_management.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.library.library_management.dto.AuthorDTO;
import org.library.library_management.dto.PageDTO;
import org.library.library_management.mapper.AuthorMapper;
import org.library.library_management.models.Author;
import org.library.library_management.payload.ApiResponse;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("${app.version}/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toAuthor(authorDTO);
        author = authorService.saveAuthor(author);
        ApiResponse response = new ApiResponse("Create success", AuthorMapper.INSTANCE.toAuthorDTO(author));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable long id) {
        Author author = authorService.getAuthorById(id);
        ApiResponse response = new ApiResponse("Get one success", AuthorMapper.INSTANCE.toAuthorDTO(author));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthor(
            @RequestParam(required = false) String name,
            @RequestParam(name = PageUtil.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = PageUtil.PAGE_SIZE, required = false) Integer size
    ) {
        Page<Author> authors = authorService.getAllAuthors(name, page, size);
        PageDTO pageDTO = new PageDTO(authors);
        ApiResponse response = new ApiResponse("Get all success", pageDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable long id) {
        authorService.deleteAuthor(id);
        Map<String, Long> data = new HashMap<>();
        data.put("id", id);
        ApiResponse response = new ApiResponse("Deleted success", data);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable long id, @Valid @RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toAuthor(authorDTO);
        author.setId(id);
        authorService.updateAuthor(author);
        ApiResponse response = new ApiResponse("Update success", AuthorMapper.INSTANCE.toAuthorDTO(author));
        return ResponseEntity.ok(response);
    }

}
