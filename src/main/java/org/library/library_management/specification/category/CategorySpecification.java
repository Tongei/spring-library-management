package org.library.library_management.specification.category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.library.library_management.models.Author;
import org.library.library_management.models.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategorySpecification implements Specification<Category> {
    private final CategoryFilter categoryFilter;

    @Override
    public Predicate toPredicate(Root<Category> category, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(categoryFilter.getName() != null && !categoryFilter.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(category.get("name")), "%"+categoryFilter.getName().toLowerCase()+"%"));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
