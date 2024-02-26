package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void findAllShouldReturnAllClients() {

        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    public static final String CLIENT_NAME = "Cliente 1";

    @Test
    public void saveNotExistsCategoryIdShouldInsert() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientService.save(null, clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    public static final Long EXISTS_CLIENT_ID = 1L;

    @Test
    public void saveExistsClientIdShouldUpdate() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.save(EXISTS_CLIENT_ID, clientDto);

        verify(clientRepository).save(client);
    }
    
    @Test
    public void deleteExistsCategoryIdShouldDelete() throws Exception {

          Client client = mock(Client.class);
          when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

          clientService.delete(EXISTS_CLIENT_ID);

          verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }
    
    public static final String EXISTS_CLIENT_NAME = "Cliente 3";
    @Test
    public void saveExistsNameShouldFail() { // comprobamos que si intentamos guardar un cliente con un nombre que ya existe, la operación de guardado falla y no se inserta un nuevo cliente

        // DTO con el nombre existente
        ClientDto clientDto = new ClientDto();
        clientDto.setName(EXISTS_CLIENT_NAME);

        // Mockeamos el comportamiento del repositorio para devolver un cliente existente con el mismo nombre
        when(clientRepository.findByName(EXISTS_CLIENT_NAME)).thenReturn(mock(Client.class));

        // Intentamos guardar el cliente con el nombre existente
        assertThrows(IllegalArgumentException.class, () -> clientService.save(null, clientDto));
    }

    @Test
    public void modifyExistingNameShouldFail() {    // verificamos que is intentamos modificar un cliente y se cambia su nombre a uno existente, operación de actualizar falla y no se modifica el cliente existente

        // DTO con el nombre existente
        ClientDto clientDto = new ClientDto();
        clientDto.setName(EXISTS_CLIENT_NAME);

        // Mockeamos el comportamiento del repositorio para devolver un cliente existente con el mismo nombre
        Client existingClient = mock(Client.class);
        when(clientRepository.findByName(EXISTS_CLIENT_NAME)).thenReturn(existingClient);

        // Intentamos modificar el cliente existente cambiando su nombre al nombre existente
        assertThrows(IllegalArgumentException.class, () -> clientService.save(EXISTS_CLIENT_ID, clientDto));
    }



}