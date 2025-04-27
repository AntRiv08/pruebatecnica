package com.minegocio.pruebatecnica.persistence.impl;

import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.persistence.IClientDAO;
import com.minegocio.pruebatecnica.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDAOImpl implements IClientDAO {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long Id) {
        return clientRepository.findById(Id);
    }

    @Override
    public List<Client> findByNameOrIdNumber(String param) {
        return clientRepository.listByNameOrCi(param);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
