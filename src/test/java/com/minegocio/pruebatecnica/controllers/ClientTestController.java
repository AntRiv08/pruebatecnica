package com.minegocio.pruebatecnica.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minegocio.pruebatecnica.controllers.dto.Client.ClientSaveDTO;
import com.minegocio.pruebatecnica.controllers.dto.Client.ClientUpdateDTO;
import com.minegocio.pruebatecnica.entities.Client;
import com.minegocio.pruebatecnica.services.IAddressService;
import com.minegocio.pruebatecnica.services.IClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ClientController.class)
public class ClientTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClientService clientService;

    @MockBean
    private IAddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Save client test")
    @Test
    void testSaveClient() throws Exception {
        ClientSaveDTO clientSaveDTO = ClientSaveDTO.builder()
                .identificationType("CEDULA")
                .identificationNumber("1710034065")
                .fullName("Antonio Rivera")
                .email("prueba1@prueba.com")
                .cellphone("0912345678")
                .build();

        Client client = Client.builder()
                .id(1L)
                .identificationType(clientSaveDTO.getIdentificationType())
                .identificationNumber(clientSaveDTO.getIdentificationNumber())
                .fullName(clientSaveDTO.getFullName())
                .email(clientSaveDTO.getEmail())
                .cellphone(clientSaveDTO.getCellphone())
                .build();

        given(clientService.save(any(Client.class)))
                .willReturn(client);

        ResultActions response = mockMvc.perform(post("/api/client/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientSaveDTO)));

        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("Get all clients")
    @Test
    void testFindAllClients() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setIdentificationType("CEDULA");
        client.setIdentificationNumber("1710034065");
        client.setFullName("Antonio Rivera");
        client.setEmail("prueba@prueba.com");
        client.setCellphone("0912345678");
        client.setAddressList(Collections.emptyList());

        when(clientService.findClients()).thenReturn(List.of(client));

        mockMvc.perform(get("/api/client/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].identificationType", is("CEDULA")))
                .andExpect(jsonPath("$[0].identificationNumber", is("1710034065")))
                .andExpect(jsonPath("$[0].fullName", is("Antonio Rivera")))
                .andExpect(jsonPath("$[0].email", is("prueba@prueba.com")))
                .andExpect(jsonPath("$[0].cellphone", is("0912345678")));
    }

    @DisplayName("List empty test")
    @Test
    void testFindAllClientsEmpty() throws Exception {
        when(clientService.findClients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/client/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }

    @DisplayName("Test find by id")
    @Test
    void testFindById() throws Exception {
        Long id = 1L;
        Client client = new Client();
        client.setId(id);
        client.setIdentificationType("CEDULA");
        client.setIdentificationNumber("1710034065");
        client.setFullName("Antonio Rivera");
        client.setEmail("prueba@prueba.com");
        client.setCellphone("0912345678");
        client.setAddressList(Collections.emptyList());

        when(clientService.findById(id)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/client/find/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.identificationType", is("CEDULA")))
                .andExpect(jsonPath("$.identificationNumber", is("1710034065")))
                .andExpect(jsonPath("$.fullName", is("Antonio Rivera")))
                .andExpect(jsonPath("$.email", is("prueba@prueba.com")))
                .andExpect(jsonPath("$.cellphone", is("0912345678")));
    }

    @DisplayName("Test find by id not found")
    @Test
    void testFindByIdNotFound() throws Exception {
        Long id = 2L;
        when(clientService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/client/find/{id}", id))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test search by argument")
    @Test
    void testSearchClients() throws Exception {
        String searchParam = "Antonio";
        Client client = new Client();
        client.setId(1L);
        client.setIdentificationType("CEDULA");
        client.setIdentificationNumber("1234567890");
        client.setFullName("Antonio Rivera");
        client.setEmail("prueba@prueba.com");
        client.setCellphone("0912345678");
        client.setAddressList(Collections.emptyList());

        when(clientService.findByNameOrIdNumber(searchParam)).thenReturn(List.of(client));

        mockMvc.perform(get("/api/client/search")
                        .param("param", searchParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].fullName", is("Antonio Rivera")))
                .andExpect(jsonPath("$[0].identificationNumber", is("1234567890")));
    }

    @DisplayName("Test update client")
    @Test
    void testUpdateClientSuccess() throws Exception {
        Long id = 1L;
        Client existingClient = new Client();
        existingClient.setId(id);

        ClientUpdateDTO updateDTO = new ClientUpdateDTO();
        updateDTO.setIdentificationType("CEDULA");
        updateDTO.setIdentificationNumber("1710034065");
        updateDTO.setFullName("Antonio Rivera Actualizado");
        updateDTO.setEmail("actualizado@correo.com");
        updateDTO.setCellphone("0912345678");

        when(clientService.findById(id)).thenReturn(Optional.of(existingClient));

        mockMvc.perform(put("/api/client/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro Actualizado"));

        verify(clientService, times(1)).updateClient(any(Client.class));
    }

    @DisplayName("Test update client not found")
    @Test
    void testUpdateClientNotFound() throws Exception {
        Long id = 99L;
        ClientUpdateDTO updateDTO = new ClientUpdateDTO();
        updateDTO.setIdentificationType("CEDULA");
        updateDTO.setIdentificationNumber("1710034065");
        updateDTO.setFullName("Nombre");
        updateDTO.setEmail("correo@prueba.com");
        updateDTO.setCellphone("0912345678");

        when(clientService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/client/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test delete client")
    @Test
    void testDeleteClientSuccess() throws Exception {
        Long id = 1L;

        doNothing().when(clientService).delete(id);

        mockMvc.perform(delete("/api/client/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro Eliminado"));

        verify(clientService, times(1)).delete(id);
    }

    @DisplayName("Test delete invalid request")
    @Test
    void testDeleteClientBadRequest() throws Exception {
        mockMvc.perform(delete("/api/client/delete/{id}", "invalid"))
                .andExpect(status().isBadRequest());
    }
}
