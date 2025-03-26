package org.library.library_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO {
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalPages;
    private long totalElements;
    private long numberOfElements;
    //
    private boolean first;
    private boolean last;
    private boolean empty;
}