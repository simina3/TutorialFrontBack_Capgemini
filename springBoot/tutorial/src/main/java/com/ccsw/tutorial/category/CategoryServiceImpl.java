package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 *         Capa de Servicios puede invocar otros servicios pero NO a un
 *         controlador.
 */
@Service // anotar Service e implementar interfaz -> MUY importante
@Transactional
public class CategoryServiceImpl implements CategoryService {

    // private long SEQUENCE = 1;
    // private Map<Long, CategoryDto> categories = new HashMap<Long, CategoryDto>();

    @Autowired
    CategoryRepository categoryRepository;  // Accede al Repository y recupera datos o los guarda.

    /** GAME
     * {@inheritDoc}
     */
    @Override
    public Category get(Long id) {

          return this.categoryRepository.findById(id).orElse(null);
    }
    
    /**
     * {@inheritDoc}
     */
    // Recupera todas las categorías
    @Override
    public List<Category> findAll() {
        // return new ArrayList<CategoryDto>(this.categories.values());
        return (List<Category>) this.categoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    // Crea o actualiza una categoría
    @Override
    public void save(Long id, CategoryDto dto) {

        Category category = null ;

        if (id == null) {
           category = new Category();   // internamente hará un save
        } else {
           //category = this.categoryRepository.findById(id).orElse(null);    //internamente hará un update
           category = this.get(id);
        }

        category.setName(dto.getName());

        this.categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    // Elimina una categoría
    @Override
    public void delete(Long id) throws Exception {

        if(this.get(id) == null){
           throw new Exception("Not exists");
        }

        this.categoryRepository.deleteById(id);
    }

}