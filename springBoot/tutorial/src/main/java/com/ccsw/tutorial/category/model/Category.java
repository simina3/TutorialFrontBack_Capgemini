package com.ccsw.tutorial.category.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author ccsw
 * 
 *         Entidad con la que vamos a persistir y recuperar información. Van
 *         siempre en la carpeta model de su funcionalidad (Categorías en este
 *         caso). Similares a DTOs pero tienen anotaciones que permiten generar
 *         consultas SQL a la BBDD.
 */
@Entity // Indica a Springboot que es una clase que implementa una Entidad de BBDD. Sin
        // ella no se pueden hacer queries.
@Table(name = "category") // Indica a JPA nombre y schema de la tabla que representa esta clase. Si nombre
                          // de la tabla es igual al de la clase no es necesaria la anotación.
public class Category {

    @Id // Junto con Generated indica a JPA que esta propiedad mapea una PK que se
        // genera con la estrategia que indique GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)// En este caso, IDENTITY: utiliza MySql o SQLServer (auto-incremental).
    @Column(name = "id", nullable = false) // Indica a JPA que mapea una columna de la tabla y especifica nombre de la columna (no necesaria).
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {

        this.name = name;
    }

}