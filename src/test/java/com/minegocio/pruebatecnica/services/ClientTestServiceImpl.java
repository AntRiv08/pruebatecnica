package com.minegocio.pruebatecnica.services;

import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.exception.ResourceNotFoundException;
import com.minegocio.pruebatecnica.persistence.IClientDAO;
import com.minegocio.pruebatecnica.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTestServiceImpl {
    @Mock
    private IClientDAO clientDAO;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
            client = Client.builder()
                    .id(1L)
                    .identificationType("CEDULA")
                    .identificationNumber("123456789")
                    .fullName("Antonio Rivera")
                    .email("prueba@prueba.com")
                    .cellphone("123456789")
                    .build();
    }

    @Test
    @DisplayName("Find all clients test")
    void testFindClients() {
        Client client1 = Client.builder()
                .identificationType("CEDULA")
                .identificationNumber("123456789")
                .fullName("Juan Perez")
                .email("prueba1@prueba.com")
                .cellphone("123456789")
                .build();
        given(clientDAO.findClients()).willReturn(List.of(client, client1));
        List<Client> clients = clientService.findClients();
        assertThat(clients).isNotNull();
        assertThat(clients.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find by id test")
    void testFindById() {
        given(clientDAO.findById(1L)).willReturn(Optional.of(client));
        Client saveClient = clientService.findById(client.getId()).get();
        assertThat(saveClient).isNotNull();
    }

    @Test
    @DisplayName("Find by argument test")
    void testFindByNameOrIdNumber() {
        when(clientDAO.findByNameOrIdNumber("Antonio Rivera")).thenReturn(List.of(client));

        List<Client> result = clientService.findByNameOrIdNumber("Antonio Rivera");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).contains("Antonio Rivera");
        verify(clientDAO).findByNameOrIdNumber("Antonio Rivera");
    }

    @Test
    @DisplayName("Update client test")
    void testUpdateClient() {
        given(clientDAO.save(client)).willReturn(client);
        client.setIdentificationType("otras");
        client.setIdentificationNumber("0987654321");
        client.setFullName("Roberto Casas");
        client.setEmail("correo1@gmail.com");
        client.setCellphone("0987654321");

        Client updateClient = clientService.updateClient(client);

        assertThat(updateClient.getFullName()).isEqualTo("Roberto Casas");
        assertThat(updateClient.getEmail()).isEqualTo("correo1@gmail.com");
    }

    @Test
    @DisplayName("Save client Test")
    void testSaveClient() {
        given(clientDAO.findById(client.getId()))
                .willReturn(Optional.empty());
        given(clientDAO.save(client)).willReturn(client);

        Client saveClient = clientService.save(client);
        assertThat(saveClient).isNotNull();
    }

    @Test
    @DisplayName("Save client with throw Execption")
    void testSaveClientExcep() {
        given(clientDAO.findById(client.getId()))
                .willReturn(Optional.of(client));

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.save(client);
        });
        verify(clientDAO, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Delete client test")
    void testDeleteClient() {
        long clientId = 1L;
        willDoNothing().given(clientDAO).delete(clientId);

        clientService.delete(clientId);
        verify(clientDAO, times(1)).delete(clientId);
    }
}
