package org.library.library_management.controllers;

import org.library.library_management.dto.AuthorDTO;
import org.library.library_management.dto.PageDTO;
import org.library.library_management.mapper.AuthorMapper;
import org.library.library_management.models.Author;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.version}/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveAuthor(@RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toAuthor(authorDTO);
        author = authorService.saveAuthor(author);
        return ResponseEntity.ok(AuthorMapper.INSTANCE.toAuthorDTO(author));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(AuthorMapper.INSTANCE.toAuthorDTO(author));
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthor(
            @RequestParam(required = false) String name,
            @RequestParam(name = PageUtil.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = PageUtil.PAGE_SIZE, required = false) Integer size
    ) {
        Page<Author> authors = authorService.getAllAuthors(name, page, size);
        PageDTO pageDTO = new PageDTO(authors);
        return ResponseEntity.ok(pageDTO);
    }
}
