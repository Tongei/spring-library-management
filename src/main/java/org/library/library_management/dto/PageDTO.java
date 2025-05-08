package org.library.library_management.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO {
    private List<?> item;
    private PaginationDTO pagination;
    public PageDTO(Page<?> page) {
        this.item = page.hasContent() ? page.getContent() : List.of();
        this.pagination = PaginationDTO.builder()
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .pageSize(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .pageNumber(page.getPageable().getPageNumber() + 1)
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }
}