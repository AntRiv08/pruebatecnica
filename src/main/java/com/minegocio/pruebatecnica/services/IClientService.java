package com.minegocio.pruebatecnica.services;

import com.minegocio.pruebatecnica.entities.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    List<Client> findClients();

    Optional<Client> findById(Long id);

    List<Client> findByNameOrIdNumber(String param);

    Client updateClient(Client client);

    Client save(Client client);

    void delete(Long id);
}
