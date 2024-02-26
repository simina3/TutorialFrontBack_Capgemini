package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 * Es la última capa, la que está más cerca de los datos finales.
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
    /**
     * Método para recuperar un listado paginado de {@link Author} -> ya no nos sirve con las operaciones básicas del CrudRepository
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */
    Page<Author> findAll(Pageable pageable); 
}
