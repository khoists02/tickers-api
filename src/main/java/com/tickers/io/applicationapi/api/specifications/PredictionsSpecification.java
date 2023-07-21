package com.tickers.io.applicationapi.api.specifications;

import com.tickers.io.applicationapi.api.criteria.PredictionsCriteria;
import com.tickers.io.applicationapi.api.criteria.TickerDetailsCriteria;
import com.tickers.io.applicationapi.model.Predictions;
import com.tickers.io.applicationapi.model.TickerDetails;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PredictionsSpecification {
    public static Specification<Predictions> predictionsQuery(final PredictionsCriteria criteria) {

        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<Predicate>();
            if (criteria.getName() != null && !criteria.getName().isEmpty() && !criteria.getName().isBlank()) {
                Expression<String> searchLikeExpression =  criteriaBuilder.lower(criteriaBuilder.literal("%" + criteria.getName() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchLikeExpression));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
