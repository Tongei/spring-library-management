package org.library.library_management.services.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageUtil {
    public Integer DEFAULT_PAGE_SIZE = 10;
    public Integer DEFAULT_PAGE_NUMBER= 1;
    public String PAGE_SIZE = "_per_page";
    public String PAGE_NUMBER = "_page";

    static Pageable getPageable(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(pageNumber-1, pageSize);
    }
}
