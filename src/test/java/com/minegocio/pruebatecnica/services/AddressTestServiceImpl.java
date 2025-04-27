package com.minegocio.pruebatecnica.services;

import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.persistence.IAddressDAO;
import com.minegocio.pruebatecnica.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressTestServiceImpl {
    @Mock
    private  IAddressDAO addressDAO;

    @InjectMocks
    AddressServiceImpl addressService;

    private Address address;
    private Client client;

    @BeforeEach
    void setup(){
        client = Client.builder()
                .id(1L)
                .build();
        address = Address.builder()
                .id(1L)
                .province("Pichincha")
                .city("Quito")
                .address("Ponceano")
                .client(client)
                .build();
    }

    @Test
    @DisplayName("Find all address test")
    void testFindClients() {
        Address address1 = Address.builder()
                .province("Guayas")
                .city("Guayaquil")
                .address("9 de Octubre")
                .client(client)
                .build();
        given(addressDAO.findAddress()).willReturn(List.of(address, address1));
        List<Address> addresses = addressService.findAddress();
        assertThat(addresses).isNotNull();
        assertThat(addresses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find by id test")
    void testFindById() {
        given(addressDAO.findById(1L)).willReturn(Optional.of(address));
        Address saveAddress = addressService.findById(address.getId()).get();
        assertThat(saveAddress).isNotNull();
    }

    @Test
    @DisplayName("Update address test")
    void testUpdateAddress() {
        Client client1 = Client.builder()
                        .id(2L).build();
        given(addressDAO.save(address)).willReturn(address);
        address.setProvince("Guayas");
        address.setCity("Guayaquil");
        address.setAddress("Ponceano");
        address.setClient(client1);

        Address updateAddress = addressService.updateAddress(address);

        assertThat(updateAddress.getProvince()).isEqualTo("Guayas");
        assertThat(updateAddress.getClient().getId()).isEqualTo(2L);
    }
    @Test
    @DisplayName("Save address Test")
    void testSaveClient() {
        given(addressDAO.findById(address.getId()))
                .willReturn(Optional.empty());
        given(addressDAO.save(address)).willReturn(address);

        Address saveAddress = addressService.save(address);
        assertThat(saveAddress).isNotNull();
    }
    @Test
    @DisplayName("Delete address test")
    void testDeleteAddress() {
        long addressId = 1L;
        willDoNothing().given(addressDAO).delete(addressId);

        addressService.delete(addressId);
        verify(addressDAO, times(1)).delete(addressId);
    }
}
