package com.minegocio.pruebatecnica.persistence;

import com.minegocio.pruebatecnica.entities.Client;

import java.util.List;
import java.util.Optional;

public interface IClientDAO {

    List<Client> findClients();

    Optional<Client> findById(Long Id);

    List<Client> findByNameOrIdNumber(String param);

    Client updateClient(Client client);

    Client save(Client client);

    void delete(Long id);
}
