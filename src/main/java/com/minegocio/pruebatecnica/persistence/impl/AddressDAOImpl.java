package com.minegocio.pruebatecnica.persistence.impl;

import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.persistence.IAddressDAO;
import com.minegocio.pruebatecnica.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressDAOImpl implements IAddressDAO {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findAddress() {
        return (List<Address>) addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
