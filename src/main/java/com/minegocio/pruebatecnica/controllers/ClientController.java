package com.minegocio.pruebatecnica.controllers;

import com.minegocio.pruebatecnica.controllers.dto.Client.ClientDTO;
import com.minegocio.pruebatecnica.controllers.dto.Client.ClientListsDTO;
import com.minegocio.pruebatecnica.controllers.dto.Client.ClientSaveDTO;
import com.minegocio.pruebatecnica.controllers.dto.Client.ClientUpdateDTO;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/findAll")
    public ResponseEntity<List<ClientListsDTO>> findAll() {
        List<ClientListsDTO> clientList = clientService.findClients()
                .stream()
                .map(client -> ClientListsDTO.builder()
                        .id(client.getId())
                        .identificationType(client.getIdentificationType())
                        .identificationNumber(client.getIdentificationNumber())
                        .fullName(client.getFullName())
                        .email(client.getEmail())
                        .cellphone(client.getCellphone())
                        .addressList(client.getAddressList())
                        .build())
                .toList();
        return ResponseEntity.ok(clientList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ClientListsDTO> findbyId(@PathVariable Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            Client clientdb = client.get();
            ClientListsDTO clientdto = ClientListsDTO.builder()
                    .id(clientdb.getId())
                    .identificationType(clientdb.getIdentificationType())
                    .identificationNumber(clientdb.getIdentificationNumber())
                    .fullName(clientdb.getFullName())
                    .email(clientdb.getEmail())
                    .cellphone(clientdb.getCellphone())
                    .addressList(clientdb.getAddressList())
                    .build();
            return ResponseEntity.ok(clientdto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientListsDTO>> search(@RequestParam String param){
        List<ClientListsDTO> CliList = clientService.findByNameOrIdNumber(param)
                .stream()
                .map(client -> ClientListsDTO.builder()
                        .id(client.getId())
                        .identificationType(client.getIdentificationType())
                        .identificationNumber(client.getIdentificationNumber())
                        .fullName(client.getFullName())
                        .email(client.getEmail())
                        .cellphone(client.getCellphone())
                        .addressList(client.getAddressList())
                        .build())
                .toList();
        return ResponseEntity.ok(CliList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO clientDTO) {
        Optional<Client> existingClient = clientService.findById(id);
        if (existingClient.isPresent()) {
            Client clientDb = existingClient.get();
            clientDb.setIdentificationType(clientDTO.getIdentificationType());
            clientDb.setIdentificationNumber(clientDTO.getIdentificationNumber());
            clientDb.setFullName(clientDTO.getFullName());
            clientDb.setEmail(clientDTO.getEmail());
            clientDb.setCellphone(clientDTO.getCellphone());
            clientService.updateClient(clientDb);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<ClientDTO> save(@Valid @RequestBody ClientSaveDTO clientDTO) throws URISyntaxException {
        clientService.save(Client.builder()
                .identificationType(clientDTO.getIdentificationType())
                .identificationNumber(clientDTO.getIdentificationNumber())
                .fullName(clientDTO.getFullName())
                .email(clientDTO.getEmail())
                .cellphone(clientDTO.getCellphone())
                .build());
        return ResponseEntity.created(new URI("/api/client/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null){
            return  ResponseEntity.badRequest().build();
        }
        clientService.delete(id);
        return  ResponseEntity.ok("Registro Eliminado");
    }

}