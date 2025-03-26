package org.library.library_management.specification;

import jakarta.persistence.criteria.*;
import lombok.Data;
import org.library.library_management.models.Author;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorSpecification implements Specification<Author> {
    private final AuthorFilter authorFilter;

    @Override
    public Predicate toPredicate(Root<Author> author, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (authorFilter.getName() != null && !authorFilter.getName().trim().isEmpty()) {
            predicates.add(cb.like(cb.upper(author.get("name")), "%" + authorFilter.getName().toUpperCase() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
