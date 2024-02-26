package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;    
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test de integración

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)     // indica a Spring cada vez que se inician los test de jUnit
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)   // indica a Spring que los test serán transaccionales, y cuando termine la ejecuciónd de cada uno, Srping hará rearranque parcial del contexto y deja estado de BBDD inicial.
public class CategoryIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/category";
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    ParameterizedTypeReference<List<CategoryDto>> responseType = new ParameterizedTypeReference<List<CategoryDto>>(){};

    @Test
    public void findAllShouldReturnAllCategories() {    // probamos findAll llamando al método GET/category comprobando que nos devuelve 3 resultados (los que hay en la bbdd inicialmente)

          ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);

          assertNotNull(response);
          assertEquals(3, response.getBody().size());
    }
    
    // Prueba de creación de una nueva Categoría
    public static final Long NEW_CATEGORY_ID = 4L;
    public static final String NEW_CATEGORY_NAME = "CAT4";

    @Test
    public void saveWithoutIdShouldCreateNewCategory() {

          CategoryDto dto = new CategoryDto();  // construimos objeto CategoryDto para dar nombre a Categoría
          dto.setName(NEW_CATEGORY_NAME);

          restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class); // invocamos a PUT sin añadir en ruta referencia a ID

          ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType); // recuperamos lista de categorías
          assertNotNull(response);
          assertEquals(4, response.getBody().size()); // comprobamos que tiene ID = 4 y es la que acabamos de crear

          CategoryDto categorySearch = response.getBody().stream().filter(item -> item.getId().equals(NEW_CATEGORY_ID)).findFirst().orElse(null);
          assertNotNull(categorySearch);
          assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }
    
    // Prueba de modificación
    // elemento existente: igual que test unitario pero modificando la categoría de ID = 3. Filtramos y vemos si se ha modificado y comprobamos que listado de registros sigue siendo 3 y no se ha creado nuevo registro.
    public static final Long MODIFY_CATEGORY_ID = 3L;

    @Test
    public void modifyWithExistIdShouldModifyCategory() {

          CategoryDto dto = new CategoryDto();
          dto.setName(NEW_CATEGORY_NAME);

          restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CATEGORY_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

          ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
          assertNotNull(response);
          assertEquals(3, response.getBody().size());

          CategoryDto categorySearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_CATEGORY_ID)).findFirst().orElse(null);
          assertNotNull(categorySearch);
          assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }
    // resultado erróneo (elemento inexistente): intentamos modificar ID=4, que no existe en bbdd y esperamos un 500 Internal Server Error al llamar al método PUT.
    @Test
    public void modifyWithNotExistIdShouldInternalError() {

          CategoryDto dto = new CategoryDto();
          dto.setName(NEW_CATEGORY_NAME);

          ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CATEGORY_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

          assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    // Prueba de borrado
    public static final Long DELETE_CATEGORY_ID = 2L;

    @Test
    public void deleteWithExistsIdShouldDeleteCategory() {

          restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_CATEGORY_ID, HttpMethod.DELETE, null, Void.class); // invocamos método DELETE

          ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
          assertNotNull(response);
          assertEquals(2, response.getBody().size()); // comprobamos que listado tiene tamaño = 2 (- 1)
    }

    @Test // comprobamos que con ID nó válido, devuelve 500 Internal Server Error
    public void deleteWithNotExistsIdShouldInternalError() {

          ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CATEGORY_ID, HttpMethod.DELETE, null, Void.class);

          assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}