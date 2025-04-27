package com.minegocio.pruebatecnica.services;

import com.minegocio.pruebatecnica.entities.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressService {

    List<Address> findAddress();

    Optional<Address> findById(Long id);

    Address updateAddress(Address address);

    Address save(Address address);

    void delete(Long id);
}
