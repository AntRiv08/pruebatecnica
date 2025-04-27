package com.minegocio.pruebatecnica.services.impl;

import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.exception.ResourceNotFoundException;
import com.minegocio.pruebatecnica.persistence.IAddressDAO;
import com.minegocio.pruebatecnica.services.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressDAO addressDAO;

    @Override
    public List<Address> findAddress() {
        return addressDAO.findAddress();
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressDAO.findById(id);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressDAO.save(address);
    }

    @Override
    public Address save(Address address) {
        Optional<Address> saveAddress = addressDAO.findById(address.getId());
        if(saveAddress.isPresent()){
            throw new ResourceNotFoundException("Dirreccion ya registrada");
        }
        return addressDAO.save(address);
    }

    @Override
    public void delete(Long id) {
        addressDAO.delete(id);
    }
}
