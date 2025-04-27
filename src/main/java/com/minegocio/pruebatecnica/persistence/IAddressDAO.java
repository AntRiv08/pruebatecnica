package com.minegocio.pruebatecnica.persistence;

import com.minegocio.pruebatecnica.entities.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressDAO {

    List<Address> findAddress();

    Optional<Address> findById(Long id);

    Address updateAddress(Address address);

    Address save(Address address);

    void delete(Long id);
}
