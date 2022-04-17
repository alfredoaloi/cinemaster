package com.cinemaster.backend.data.specification;

import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventSpecification {

    public static Specification<Event> findAllBy(Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return (Specification<Event>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("room"), room));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("date"), date));

            Predicate startTimePredicate = criteriaBuilder.conjunction();
            startTimePredicate = criteriaBuilder.and(startTimePredicate, criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), startTime));
            startTimePredicate = criteriaBuilder.and(startTimePredicate, criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), startTime));

            Predicate endTimePredicate = criteriaBuilder.conjunction();
            endTimePredicate = criteriaBuilder.and(endTimePredicate, criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endTime));
            endTimePredicate = criteriaBuilder.and(endTimePredicate, criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), endTime));

            Predicate timePredicate = criteriaBuilder.or(startTimePredicate, endTimePredicate);
            predicate = criteriaBuilder.and(predicate, timePredicate);

            return criteriaQuery.where(predicate).getRestriction();
        };
    }
}
