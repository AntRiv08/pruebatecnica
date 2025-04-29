package com.minegocio.pruebatecnica.controllers.dto.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressSaveDTO {
    @NotBlank(message = "El campo provincia es obligatorio")
    private String province;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String city;

    @NotBlank(message = "El campo provincia es obligatorio")
    private String address;

//    @NotNull
    private Long clientId;
}
