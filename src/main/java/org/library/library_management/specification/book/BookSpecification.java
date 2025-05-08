package org.library.library_management.specification.book;

import jakarta.persistence.criteria.*;
import lombok.Data;
import org.library.library_management.models.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookSpecification implements Specification<Book> {
    private final BookFilter bookFilter;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (bookFilter.getTitle() != null && !bookFilter.getTitle().trim().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("title")), "%" + bookFilter.getTitle().toUpperCase() + "%"));
        }

        if (bookFilter.getAuthorId() != null) {
            predicates.add(cb.equal(root.get("author").get("id"), bookFilter.getAuthorId()));
        }

        if (bookFilter.getCategoryId() != null) {
            predicates.add(cb.equal(root.get("category").get("id"), bookFilter.getCategoryId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

