package com.minegocio.pruebatecnica.controllers.dto.Address;

import com.minegocio.pruebatecnica.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;

    private String province;

    private String city;

    private String address;

    private Client client;
}
