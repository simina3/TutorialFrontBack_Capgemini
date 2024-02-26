package com.ccsw.tutorial.category;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Clase para definir las rutas de las operaciones
// Un controlador simplemente debe invocar servicios y transformar ciertos datos.

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ccsw
 * 
 *         Tago y Operation nos ayudarán a generar documentación automática de
 *         nuestras APIs para que el código sea más mantenible y la
 *         documentación más fiable
 */
@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category") // definición de ruta general del controlador
@RestController // ops de tipo Rest
@CrossOrigin(origins = "*")
public class CategoryController {

    // Utilizamos el CategoryDto para implementar las tres operaciones de negocio
    // (listar, actualizar/guardar y borrar)

    // variables que simulan una BBDD y una secuencia
    // private long SEQUENCE = 1;
    // private Map<Long, CategoryDto> categories = new HashMap<Long, CategoryDto>();

    @Autowired // inyectar y utilizar componentes manejados por Spring Boot. NO crear objeto de
               // CategoryServiceImpl ni hacer new.
    CategoryService categoryService;
    @Autowired
    ModelMapper mapper; // conversor DozerBeanMapper para traducir una lista a un tipo concreto o un
                        // objeto único a un tipo concreto. Conversiones siempre por nombre: propiedades
                        // del objeto destino deben llamarse IGUAL que las propiedades del objeto
                        // origen, si no se quedarán a null.

    /**
     * Método para recuperar todas las categorias
     *
     * @return {@link List} de {@link CategoryDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @RequestMapping(path = "", method = RequestMethod.GET) // path define un path para la operación sumándole el path de
                                                           // la clase, method deifne verbo http que atenderemos
    public List<CategoryDto> findAll() {

        // return new ArrayList<CategoryDto>(this.categories.values());
        // return this.categoryService.findAll(); // llamamos al servicio quien se
        // encargará de la lógica del negocio

        List<Category> categories = this.categoryService.findAll();

        return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una categoria
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT) // dos rutas: normal para create (category/) e
                                                                        // informada para update (category/3).
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {

        /*
         * CategoryDto category;
         * 
         * if (id == null) { category = new CategoryDto();
         * category.setId(this.SEQUENCE++); this.categories.put(category.getId(),
         * category); } else { category = this.categories.get(id); }
         * 
         * category.setName(dto.getName());
         */

        this.categoryService.save(id, dto);
    }

    /**
     * Método para borrar una categoria
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        // this.categories.remove(id);

        this.categoryService.delete(id);
    }
}

/**
 * GET -> recuperar información POST -> update y filtrados complejos de
 * información PUT -> save de información DELETE -> borrados de información
 */