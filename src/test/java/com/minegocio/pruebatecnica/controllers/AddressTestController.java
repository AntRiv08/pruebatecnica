package com.minegocio.pruebatecnica.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minegocio.pruebatecnica.controllers.dto.Address.AddressDTO;
import com.minegocio.pruebatecnica.controllers.dto.Address.AddressSaveDTO;
import com.minegocio.pruebatecnica.entities.Address;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.services.IAddressService;
import com.minegocio.pruebatecnica.services.IClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AddressController.class)
public class AddressTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAddressService addressService;

    @MockBean
    private IClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test find all addresses")
    @Test
    void testFindAll() throws Exception {

        Client client = new Client();
        client.setId(1L);

        Address address1 = new Address();
        address1.setId(1L);
        address1.setProvince("Pichincha");
        address1.setCity("Quito");
        address1.setAddress("Av. Amazonas N34-120");
        address1.setClient(client);

        when(addressService.findAddress()).thenReturn(List.of(address1));

        mockMvc.perform(get("/api/address/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].province").value("Pichincha"))
                .andExpect(jsonPath("$[0].city").value("Quito"))
                .andExpect(jsonPath("$[0].address").value("Av. Amazonas N34-120"));

        verify(addressService, times(1)).findAddress();
    }

    @DisplayName("Test list empty")
    @Test
    void testFindEmptyList() throws Exception {
        when(addressService.findAddress()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/address/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(0));

        verify(addressService, times(1)).findAddress();
    }

    @DisplayName("Test find by id address")
    @Test
    void testFindById() throws Exception {
        Address address = new Address();
        address.setId(1L);
        address.setProvince("Pichincha");
        address.setCity("Quito");
        address.setAddress("Av. Amazonas");
        address.setClient(null);

        Mockito.when(addressService.findById(1L)).thenReturn(Optional.of(address));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/address/find/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.province").value("Pichincha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Quito"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Av. Amazonas"));
    }

    @DisplayName("Test bad request with id")
    @Test
    void testBadRequest() throws Exception {
        Mockito.when(addressService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/address/find/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Test update address")
    @Test
    void testUpdateAddress() throws Exception {
        Address address = new Address();
        address.setId(1L);
        address.setProvince("Pichincha");
        address.setCity("Quito");
        address.setAddress("Av. Amazonas");
        address.setClient(null);

        AddressDTO updatedAddress = AddressDTO.builder()
                .id(1L)
                .province("Guayas")
                .city("Guayaquil")
                .address("Malecón 2000")
                .client(null)
                .build();

        Mockito.when(addressService.findById(1L)).thenReturn(Optional.of(address));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/address/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAddress)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registro Actualizado"));

        Mockito.verify(addressService).updateAddress(Mockito.any(Address.class));
    }

    @DisplayName("Save client test")
    @Test
    void testSaveAddress() throws Exception {
        AddressSaveDTO addressDTO = AddressSaveDTO.builder()
                .province("Pichincha")
                .city("Quito")
                .address("Av. Naciones Unidas")
                .clientId(1L)
                .build();

        Client client = new Client();
        client.setId(1L);
        Mockito.when(clientService.findById(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/address/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "api/address/save"));
        Mockito.verify(addressService).save(Mockito.any(Address.class));
    }

    @DisplayName("Test delete Address")
    @Test
    void testDeleteAddress() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/address/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registro Eliminado"));

        Mockito.verify(addressService).delete(id);
    }
    
}
