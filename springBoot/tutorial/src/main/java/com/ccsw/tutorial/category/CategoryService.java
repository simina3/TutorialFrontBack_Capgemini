package com.ccsw.tutorial.category;

import java.util.List;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;

/**
 * @author ccsw
 * 
 *         Servicio para implementar la lógica de negocio, operaciones y demás.
 *         Patrón fachada: necesitamos una Interface y al menos una
 *         implementación de ésta, e interactuar con ella para en un futuro
 *         sustituir implementación por otra sin afectar al resto del código.
 */
public interface CategoryService {  // objetos de respuesta son de tipo Category y los de entrada CategoryDto

    /** GAME
     * Recupera una {@link Category} a partir de su ID para  así poder asignarlos al objeto Game
     *
     * @param id PK de la entidad
     * @return {@link Category}
     */
    Category get(Long id);
    
    /**
     * Método para recuperar todas las {@link Category}
     *
     * @return {@link List} de {@link Category}
     */
    List<Category> findAll();   

    /**
     * Método para crear o actualizar una {@link Category}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, CategoryDto dto);

    /**
     * Método para borrar una {@link Category}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}