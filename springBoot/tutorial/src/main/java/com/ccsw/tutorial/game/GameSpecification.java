package com.ccsw.tutorial.game;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// Especificación de la clase Game que contiene la construcción de la consulta en función de los criterios que se le proporcionan.
// Recoge un criterio filtrado y construye un predicado (que genera comparaciones)

public class GameSpecification implements Specification<Game> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria; // al crear un Specification debemos generar un predicado, por lo que necesitamos unos criterios de filtrado para poder generarlo

    public GameSpecification(SearchCriteria criteria) {

        this.criteria = criteria; // pasamos criterios de filtrado
    }

    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) { // selecciona el tipo de operación (en este caso comparación -> :) Otras operaciones: >, <>...
            Path<String> path = getPath(root);
            if (path.getJavaType() == String.class) { // construcción de un Predicate que al ser de tipo comparación
                return builder.like(path, "%" + criteria.getValue() + "%"); // si es texto, hace un like
            } else {
                return builder.equal(path, criteria.getValue()); // si no es texto (número o fecha), hará un equals
            }
        }
        return null;
    }

    // Función que nos permite explorar las sub-entidades para realizar consultas sobre los atributos de estas
    private Path<String> getPath(Root<Game> root) { 
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}