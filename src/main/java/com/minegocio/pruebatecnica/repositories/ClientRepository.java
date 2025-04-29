package com.minegocio.pruebatecnica.repositories;

import com.minegocio.pruebatecnica.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(c.identificationNumber) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Client> listByNameOrCi(String parameter);

    Client findByEmail(String email);
}
