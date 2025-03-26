package org.library.library_management.services.impl;

import org.library.library_management.exception.ResourceNotFoundException;
import org.library.library_management.models.Author;
import org.library.library_management.repositories.AuthorRepository;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.util.PageUtil;
import org.library.library_management.specification.AuthorFilter;
import org.library.library_management.specification.AuthorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id, "Author"));
    }

    @Override
    public Page<Author> getAllAuthors(String name, Integer page, Integer size) {
        AuthorFilter filter = new AuthorFilter();
        filter.setName(name);
        Specification<Author> spec = new AuthorSpecification(filter);
        Pageable pageable = PageUtil.getPageable(page, size);
        return authorRepository.findAll(spec, pageable);
    }
}
