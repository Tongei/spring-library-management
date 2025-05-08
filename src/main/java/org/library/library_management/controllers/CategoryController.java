package org.library.library_management.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.library.library_management.dto.CategoryDTO;
import org.library.library_management.dto.PageDTO;
import org.library.library_management.mapper.CategoryMapper;
import org.library.library_management.models.Category;
import org.library.library_management.payload.ApiResponse;
import org.library.library_management.services.CategoryService;
import org.library.library_management.services.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${app.version}/categories")
@RolesAllowed("ROLE_ADMIN")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        category = categoryService.save(category);
        ApiResponse response = new ApiResponse("Create success!", category);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        ApiResponse response = new ApiResponse("Get success!", CategoryMapper.INSTANCE.toCategoryDTO(categoryService.getById(id)));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(required = false) String name,
            @RequestParam(name = PageUtil.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = PageUtil.PAGE_SIZE, required = false) Integer size
    ) {
        Page<Category> categories = categoryService.getAll(name, page, size);
        PageDTO pageDTO = new PageDTO(categories);
        ApiResponse response = new ApiResponse("Get all success!", pageDTO);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        Map<String, Long> data = new HashMap<>();
        data.put("id", id);
        ApiResponse response = new ApiResponse("Delete success!", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        category.setId(id);
        ApiResponse response = new ApiResponse("Update success!", CategoryMapper.INSTANCE.toCategoryDTO(categoryService.save(category)));
        return ResponseEntity.ok(response);
    }

}
