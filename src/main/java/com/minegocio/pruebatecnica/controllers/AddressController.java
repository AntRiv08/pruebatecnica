package com.minegocio.pruebatecnica.controllers;

import com.minegocio.pruebatecnica.controllers.dto.Address.AddressDTO;
import com.minegocio.pruebatecnica.controllers.dto.Address.AddressSaveDTO;
import com.minegocio.pruebatecnica.controllers.dto.Address.AddressUpdateDTO;
import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.services.IAddressService;
import com.minegocio.pruebatecnica.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/findAll")
    public ResponseEntity<List<AddressDTO>> findAll() {
        List<AddressDTO> addressList = addressService.findAddress()
                .stream()
                .map(address -> AddressDTO.builder()
                        .id(address.getId())
                        .province(address.getProvince())
                        .city(address.getCity())
                        .address(address.getAddress())
                        .client(address.getClient())
                        .build())
                .toList();
        return ResponseEntity.ok(addressList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        Optional<Address> address = addressService.findById(id);
        if (address.isPresent()) {
            Address addressdb = address.get();
            AddressDTO addresdto = AddressDTO.builder()
                    .id(addressdb.getId())
                    .province(addressdb.getProvince())
                    .city(addressdb.getCity())
                    .address(addressdb.getAddress())
                    .client(addressdb.getClient())
                    .build();
            return ResponseEntity.ok(addresdto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody AddressUpdateDTO addressDTO) {
        Optional<Address> existingAddress = addressService.findById(id);
        if (existingAddress.isPresent()) {
            Address addressdb = existingAddress.get();
            addressdb.setProvince(addressDTO.getProvince());
            addressdb.setCity(addressDTO.getCity());
            addressdb.setAddress(addressDTO.getAddress());
            addressdb.setClient(addressdb.getClient());
            addressService.updateAddress(addressdb);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<AddressDTO> save(@Valid @RequestBody AddressSaveDTO addressdto) throws URISyntaxException {
        Client client = clientService.findById(addressdto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        addressService.save(Address.builder()
                .province(addressdto.getProvince())
                .city(addressdto.getCity())
                .address(addressdto.getAddress())
                .client(client)
                .build());
        return ResponseEntity.created(new URI("api/address/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        addressService.delete(id);
        return ResponseEntity.ok("Registro Eliminado");
    }


}
