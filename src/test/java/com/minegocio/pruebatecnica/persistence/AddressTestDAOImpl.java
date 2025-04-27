package com.minegocio.pruebatecnica.persistence;

import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.persistence.impl.AddressDAOImpl;
import com.minegocio.pruebatecnica.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AddressTestDAOImpl {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressDAOImpl addressDAO;

    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Client client = Client.builder().id(1L).build();
        address = Address.builder()
                .id(1L)
                .province("Pichincha")
                .city("Quito")
                .address("Ponceano")
                .client(client)
                .build();
    }

    @Test
    @DisplayName("Test find all addresses")
    void testFindAddress() {
        when(addressRepository.findAll()).thenReturn(List.of(address));

        List<Address> addresses = addressDAO.findAddress();

        assertThat(addresses).hasSize(1);
        assertThat(addresses.get(0).getProvince()).isEqualTo("Pichincha");
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test find address by ID")
    void testFindById() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Optional<Address> found = addressDAO.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getCity()).isEqualTo("Quito");
        verify(addressRepository).findById(1L);
    }

    @Test
    @DisplayName("Test save address")
    void testSave() {
        addressDAO.save(address);

        verify(addressRepository, times(1)).save(address);
    }

    @Test
    @DisplayName("Test update address")
    void testUpdateAddress() {
        when(addressRepository.save(address)).thenReturn(address);

        Address updated = addressDAO.updateAddress(address);

        assertThat(updated.getAddress()).isEqualTo("Ponceano");
        verify(addressRepository).save(address);
    }

    @Test
    @DisplayName("Test delete address by ID")
    void testDelete() {
        addressDAO.delete(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }
}
