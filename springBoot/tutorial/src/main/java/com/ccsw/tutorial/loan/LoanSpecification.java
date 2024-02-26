package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.data.jpa.domain.Specification;

public class LoanSpecification implements Specification<Loan> {
    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null) {
            return builder.greaterThanOrEqualTo(root.<LocalDate>get(criteria.getKey()),
                    (LocalDate) criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null) {
            Path<LocalDate> path = root.get(criteria.getKey());

            try {
                LocalDate date = LocalDate.parse(criteria.getValue().toString());
                return builder.lessThanOrEqualTo(path, date);
            } catch (DateTimeParseException e) {
                e.printStackTrace(); // Manejar el error de parseo de fecha aquí
            }
        }

        return null;
    }

    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}
