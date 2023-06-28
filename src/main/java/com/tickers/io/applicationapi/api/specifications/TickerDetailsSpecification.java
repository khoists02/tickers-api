package com.tickers.io.applicationapi.api.specifications;

import com.tickers.io.applicationapi.api.criteria.TickerDetailsCriteria;
import com.tickers.io.applicationapi.model.TickerDetails;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TickerDetailsSpecification {
    public static Specification<TickerDetails> tickerDetailsQuery(final TickerDetailsCriteria criteria) {

        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<Predicate>();
            if (criteria.getSics() != null && criteria.getSics().length != 0) {
                for (int i = 0; i < criteria.getSics().length; i++) {
                    Expression<String> searchLikeExpression =  criteriaBuilder.lower(criteriaBuilder.literal("%" + criteria.getSics()[i] + "%"));
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("sicDescription")), searchLikeExpression));
                }
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
