package com.tickers.io.applicationapi.api.specifications;

import com.tickers.io.applicationapi.api.criteria.TickersSearchCriteria;
import com.tickers.io.applicationapi.model.Tickers;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TickersSpecification {
    public static Specification<Tickers> tickersQuery(final TickersSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            CriteriaQuery<Tickers> cq = criteriaBuilder.createQuery(Tickers.class);
            Root<Tickers> tickers = cq.from(Tickers.class);
            final List<Predicate> predicates = new ArrayList<Predicate>();
            if (criteria.getTicker() != null) {
                predicates.add(criteriaBuilder.equal(root.get(String.valueOf(tickers.get("ticker"))), criteria.getTicker()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
