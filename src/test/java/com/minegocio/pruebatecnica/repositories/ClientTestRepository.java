package com.minegocio.pruebatecnica.repositories;

import com.minegocio.pruebatecnica.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientTestRepository {

    @Autowired
    private ClientRepository clientRepository;

    private  Client client;

    @BeforeEach
    void setup(){
        client = Client.builder()
                .identificationType("CEDULA")
                .identificationNumber("123456789")
                .fullName("Antonio Rivera")
                .email("prueba@prueba.com")
                .cellphone("123456789")
                .build();
    }

    @DisplayName("Test Save Client")
    @Test
    void testSaveClient() {
        Client saveClient = clientRepository.save(client);
        assertThat(saveClient).isNotNull();
        assertThat(saveClient.getId()).isGreaterThan(0);
    }

    @DisplayName("Test List Client")
    @Test
    void testListClient() {
        clientRepository.deleteAll();
        Client client1 = Client.builder()
                .identificationType("CEDULA")
                .identificationNumber("0987654321")
                .fullName("Alexander Ibarra")
                .email("prueba2@prueba.com")
                .cellphone("0987654321")
                .build();
        clientRepository.save(client);
        clientRepository.save(client1);

        List<Client> listClient = (List<Client>) clientRepository.findAll();
        assertThat(listClient).isNotNull();
        assertThat(listClient.size()).isEqualTo(2);
    }

    @DisplayName("Test list client by argument")
    @Test
    void testListClientByArgum() {

        List<Client> listClient = (List<Client>) clientRepository.listByNameOrCi("09");
        assertThat(listClient).isNotNull();
    }

    @DisplayName("Test get client by id")
    @Test
    void testGetClientById() {
        clientRepository.deleteAll();
        clientRepository.save(client);
        Client clientDb = clientRepository.findById(client.getId()).get();
        assertThat(clientDb).isNotNull();
    }

    @DisplayName("Test update client")
    @Test
    void testUpdateClient() {
        clientRepository.save(client);
        Client saveClient = clientRepository.findById(client.getId()).get();
        saveClient.setIdentificationType("Pasaporte");
        saveClient.setIdentificationNumber("987654321");
        saveClient.setFullName("Pablo Lopez");
        saveClient.setEmail("pablo@gmail.com");
        saveClient.setCellphone("0987654321");
        Client updateClient = clientRepository.save(saveClient);

        assertThat(updateClient.getIdentificationType()).isEqualTo("Pasaporte");
        assertThat(updateClient.getIdentificationNumber()).isEqualTo("987654321");
        assertThat(updateClient.getFullName()).isEqualTo("Pablo Lopez");
        assertThat(updateClient.getEmail()).isEqualTo("pablo@gmail.com");
        assertThat(updateClient.getCellphone()).isEqualTo("0987654321");
    }

    @DisplayName("Test delete client")
    @Test
    void testDeleteClient() {
        clientRepository.save(client);
        clientRepository.deleteById(client.getId());
        Optional<Client> exist = clientRepository.findById(client.getId());
        assertThat(exist).isEmpty();
    }
}
