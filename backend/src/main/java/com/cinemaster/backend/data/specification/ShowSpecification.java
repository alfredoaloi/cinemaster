package com.cinemaster.backend.data.specification;

import com.cinemaster.backend.data.entity.Category;
import com.cinemaster.backend.data.entity.Show;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ShowSpecification {

    public static Specification<Show> findAllByFilter(Filter filter) {
        return (Specification<Show>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filter.name != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.name + "%"));
            }
            if (filter.categories.size() > 0) {
                ListJoin<Show, Category> listJoin = root.joinList("categories");
                predicate = criteriaBuilder.and(predicate, listJoin.get("name").in(filter.categories));
            }
            if (filter.comingSoon != null && filter.comingSoon == true) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("comingSoon")));
            }
            if (filter.comingSoon != null && filter.comingSoon == false) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isFalse(root.get("comingSoon")));
            }
            if (filter.highlighted != null && filter.highlighted == true) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("highlighted")));
            }
            return criteriaQuery.where(predicate).distinct(true).getRestriction();
        };
    }

    public static class Filter {
        private String name;
        private List<String> categories;
        private Boolean comingSoon;
        private Boolean highlighted;

        public Filter(String name, Boolean comingSoon, Boolean highlighted, String... categories) {
            if (name == null) {
                this.name = null;
            } else {
                this.name = name.toLowerCase();
            }
            this.categories = new ArrayList<>();
            if (categories != null) {
                for (String category : categories) {
                    this.categories.add(category);
                }
            }
            this.comingSoon = comingSoon;
            this.highlighted = highlighted;
        }
    }

}
