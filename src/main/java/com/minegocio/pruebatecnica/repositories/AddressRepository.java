package com.minegocio.pruebatecnica.repositories;

import com.minegocio.pruebatecnica.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
