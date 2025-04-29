package com.minegocio.pruebatecnica.controllers.dto.Address;

import com.minegocio.pruebatecnica.entities.Client;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El campo provincia es obligatorio")
    private String province;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String city;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String address;

    private Client client;
}
