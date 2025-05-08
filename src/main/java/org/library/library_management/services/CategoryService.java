package org.library.library_management.services;

import org.library.library_management.models.Author;
import org.library.library_management.models.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    public Category save(Category category);
    public Category getById(long id);
    public Page<Category> getAll(String name, Integer page, Integer size);
    public void deleteById(long id);
    public void updateById(Category category);
}
