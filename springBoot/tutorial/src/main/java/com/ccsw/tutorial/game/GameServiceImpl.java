package com.ccsw.tutorial.game;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author ccsw
 *
 * Capa lógica de negocio
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;
    
    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;
    
    @Override
    public Game get(Long id) {

          return this.gameRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory) { // método que recibe los dos filtros 

        //return this.gameRepository.findAll();  // listado con filtros 
        // creamos los dos criterios de filtrado que necesitamos (title, atributo de la entidad Game, y el id de categoría, atributo de la entidad asociada a Game)
        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory)); // al no ser atributo directo de la entidad Game, navegamos hasta el atributo id a través del atributo category utilizando getPath de Specification

        // con los dos predicados anteriores generamos el Specification global para la consulta uniéndolos mediante el op AND
        Specification<Game> spec = Specification.where(titleSpec).and(categorySpec);

        return this.gameRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) { // nos llega del cliente un GameDto, que internamente tiene un AuthorDto y un CategoryDto -> hay que traducirlo a entidades de BBDD

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            //game = this.gameRepository.findById(id).orElse(null);
            game = this.get(id);
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category"); //ignoramos el ID de los objetos hijo que nos llegan de cliente (en vez de el resto de info de la entidad)

        game.setAuthor(authorService.get(dto.getAuthor().getId()));
        game.setCategory(categoryService.get(dto.getCategory().getId()));
        
        this.gameRepository.save(game);
    }

}
