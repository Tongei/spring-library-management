package org.library.library_management.mapper;

import org.library.library_management.dto.AuthorDTO;
import org.library.library_management.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    Author toAuthor(AuthorDTO authorDTO);
    AuthorDTO toAuthorDTO(Author author);
}
