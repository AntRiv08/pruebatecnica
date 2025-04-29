package com.minegocio.pruebatecnica.controllers.dto.Address;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUpdateDTO {

    @NotBlank(message = "El campo provincia es obligatorio")
    private String province;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String city;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String address;

}
