package com.minegocio.pruebatecnica.repositories;

import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressTestRepository {
    @Autowired AddressRepository addressRepository;

    private Address address;
    private Client client;


    @BeforeEach
    void setup(){

        client = Client.builder()
                .id(1L)
                .build();
        address = Address.builder()
                .province("Pichincha")
                .city("Quito")
                .address("Ponceano")
                .client(client)
                .build();
    }

    @DisplayName("Test Save Address")
    @Test
    void testSaveAddress(){
        Address saveAddress = addressRepository.save(address);
        assertThat(saveAddress).isNotNull();
        assertThat(saveAddress.getId()).isGreaterThan(0);
    }

    @DisplayName("Test List Address")
    @Test
    void testListAddress() {
        addressRepository.deleteAll();
        Address address1 = Address.builder()
                .province("Guayas")
                .city("Guayaquil")
                .address("La 8")
                .client(client)
                .build();
        addressRepository.save(address1);
        addressRepository.save(address);

        List<Address> listAddress = (List<Address>) addressRepository.findAll();
        assertThat(listAddress).isNotNull();
        assertThat(listAddress.size()).isEqualTo(2);
    }

    @DisplayName("Test get address by id")
    @Test
    void testGetAddressById() {
        addressRepository.deleteAll();
        addressRepository.save(address);
        Address addressdb = addressRepository.findById(address.getId()).get();
        assertThat(addressdb).isNotNull();
    }

    @DisplayName("Test update address")
    @Test
    void testUpdateAddress() {
        addressRepository.save(address);
        Address saveAddress = addressRepository.findById(address.getId()).get();
        saveAddress.setProvince("Santa Elena");
        saveAddress.setCity("Santa Elena");
        saveAddress.setAddress("Libertad");
        saveAddress.setClient(client);
        Address updateAddress = addressRepository.save(saveAddress);

        assertThat(updateAddress.getProvince()).isEqualTo("Santa Elena");
        assertThat(updateAddress.getCity()).isEqualTo("Santa Elena");
        assertThat(updateAddress.getAddress()).isEqualTo("Libertad");
        assertThat(updateAddress.getClient().getId()).isEqualTo(1L);
    }

    @DisplayName("Test delete address")
    @Test
    void testDeleteAddress() {
        addressRepository.save(address);
        addressRepository.deleteById(address.getId());
        Optional<Address> exist = addressRepository.findById(address.getId());
        assertThat(exist).isEmpty();
    }
}
