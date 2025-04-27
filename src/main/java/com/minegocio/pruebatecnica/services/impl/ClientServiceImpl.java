package com.minegocio.pruebatecnica.services.impl;

import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.exception.ResourceNotFoundException;
import com.minegocio.pruebatecnica.persistence.IClientDAO;
import com.minegocio.pruebatecnica.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientDAO clientDAO;

    @Override
    public List<Client> findClients() {
        return clientDAO.findClients();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientDAO.findById(id);
    }

    @Override
    public List<Client> findByNameOrIdNumber(String param) {
        return clientDAO.findByNameOrIdNumber(param);
    }

    @Override
    public Client updateClient(Client client) {
        return clientDAO.save(client);
    }

    @Override
    public Client save(Client client) {
        Optional<Client> saveClient = clientDAO.findById(client.getId());
        if(saveClient.isPresent()){
            throw new ResourceNotFoundException("El cliente ya esta registrado");
        }
        return clientDAO.save(client);
    }

    @Override
    public void delete(Long id) {
        clientDAO.delete(id);
    }
}
