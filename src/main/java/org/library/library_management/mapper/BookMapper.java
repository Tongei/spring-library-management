package org.library.library_management.mapper;

import org.library.library_management.dto.book.BookRequestDTO;
import org.library.library_management.dto.book.BookResponseDTO;
import org.library.library_management.models.Book;
import org.library.library_management.services.AuthorService;
import org.library.library_management.services.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorService.class, CategoryService.class})
public interface BookMapper {
    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "category", source = "categoryId")
    Book toBook(BookRequestDTO bookRequestDTO);

    BookResponseDTO toBookResponseDTO(Book book);
}
