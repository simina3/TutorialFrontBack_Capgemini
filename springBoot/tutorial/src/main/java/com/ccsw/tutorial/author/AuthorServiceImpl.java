package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;     // Se utiliza cuando queremos copiar más de un dato de una clase a otra (siempre que las propiedades de ambas clases se llamen igual)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 * Capa donde se implementa toda la lógica de negocio, hace uso del Repository para acceder a los datos, y recibe llamadas generalmente de los Controller.
 * Delegamos muuchas operaciones de consulta y guardado de datos en AuthorRepository.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;
       
    /** GAME
     * {@inheritDoc}
     */
    @Override
    public Author get(Long id) {

        return this.authorRepository.findById(id).orElse(null);
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {

        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, AuthorDto data) {

        Author author;

        if (id == null) {
            author = new Author();
        } else {
            //author = this.authorRepository.findById(id).orElse(null);
            author = this.get(id);
        }

        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if(this.get(id) == null){
            throw new Exception("Not exists");
        }

        this.authorRepository.deleteById(id);
    }
    
    /** para listado filtrado
     * {@inheritDoc}
     */
    @Override
    public List<Author> findAll() {

        return (List<Author>) this.authorRepository.findAll();
    }

}