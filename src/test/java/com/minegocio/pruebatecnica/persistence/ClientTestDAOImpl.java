package com.minegocio.pruebatecnica.persistence;

import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.persistence.impl.ClientDAOImpl;
import com.minegocio.pruebatecnica.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientTestDAOImpl {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientDAOImpl clientDAO;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = Client.builder()
                .id(1L)
                .fullName("Juan Pérez")
                .identificationNumber("0102030405")
                .build();
    }

    @Test
    @DisplayName("Test find all clients")
    void testFindClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<Client> clients = clientDAO.findClients();

        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getFullName()).isEqualTo("Juan Pérez");
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test find client by ID")
    void testFindById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> found = clientDAO.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Test save client")
    void testSaveClient() {
        clientDAO.save(client);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    @DisplayName("Test update client")
    void testUpdateClient() {
        when(clientRepository.save(client)).thenReturn(client);
        Client updated = clientDAO.updateClient(client);

        assertThat(updated).isEqualTo(client);
        verify(clientRepository).save(client);
    }

    @Test
    @DisplayName("Test delete client")
    void testDeleteClient() {
        clientDAO.delete(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test find by name or ID number")
    void testFindByNameOrIdNumber() {
        when(clientRepository.listByNameOrCi("Juan")).thenReturn(List.of(client));
        List<Client> results = clientDAO.findByNameOrIdNumber("Juan");

        assertThat(results).hasSize(1);
        verify(clientRepository).listByNameOrCi("Juan");
    }
}
