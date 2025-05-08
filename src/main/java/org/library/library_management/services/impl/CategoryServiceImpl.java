package org.library.library_management.services.impl;

import org.library.library_management.exception.ResourceNotFoundException;
import org.library.library_management.models.Author;
import org.library.library_management.models.Category;
import org.library.library_management.payload.ApiResponse;
import org.library.library_management.repositories.CategoryRepository;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.CategoryService;
import org.library.library_management.services.util.PageUtil;
import org.library.library_management.specification.category.CategoryFilter;
import org.library.library_management.specification.category.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(id, "Category"));
    }

    @Override
    public Page<Category> getAll(String name, Integer page, Integer size) {
        CategoryFilter filter = new CategoryFilter();
        filter.setName(name);
        Specification<Category> spec = new CategorySpecification(filter);
        Pageable pageable = PageUtil.getPageable(page, size);
        return categoryRepository.findAll(spec, pageable);
    }

    @Override
    public void deleteById(long id) {
        getById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateById(Category category) {
        getById(category.getId());
        categoryRepository.save(category);
    }
}
