package org.library.library_management.mapper;

import org.library.library_management.dto.CategoryDTO;
import org.library.library_management.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    Category toCategory(CategoryDTO categoryDTO);
    CategoryDTO toCategoryDTO(Category category);
}
