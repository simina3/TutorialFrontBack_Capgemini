package com.ccsw.tutorial.category;

import org.springframework.data.repository.CrudRepository; // implementación por defecto que ya tiene Spring de CrudRep

import com.ccsw.tutorial.category.model.Category;

/**
 * @author ccsw
 *
 *         Repositorio para acceder a los datos de la BBDD desde Java. Devuelve
 *         objeto de tipo Category y Service y Controller de tipo CategoryDto
 */
public interface CategoryRepository extends CrudRepository<Category, Long> { // extendemos la interfaz Crud pasándole
                                                                             // como tipos Entity y tipo de la PK

}