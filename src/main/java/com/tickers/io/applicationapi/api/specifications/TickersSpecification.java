package com.tickers.io.applicationapi.api.specifications;

import com.tickers.io.applicationapi.api.criteria.TickersSearchCriteria;
import com.tickers.io.applicationapi.model.Tickers;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TickersSpecification {
    public static Specification<Tickers> tickersQuery(final TickersSearchCriteria criteria) {

        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<Predicate>();
            if (criteria.getTicker() != null) {
                predicates.add(criteriaBuilder.equal(root.get("ticker"), criteria.getTicker()));
            }

            if (criteria.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), criteria.getType()));
            }

            if (criteria.getSearch() != null) {
                Expression<String> searchLikeExpression =  criteriaBuilder.lower(criteriaBuilder.literal("%" + criteria.getSearch() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchLikeExpression));
            }
            predicates.add(criteriaBuilder.isNotNull(root.get("tickerDetails")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
