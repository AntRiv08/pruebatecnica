package com.minegocio.pruebatecnica.controllers.dto.Client;

import com.minegocio.pruebatecnica.validators.OnlyNumbers;
import com.minegocio.pruebatecnica.validators.ValidIdentification;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientSaveDTO {
    @NotBlank(message = "El campo es obligatorio")
    private String identificationType;

    @NotBlank(message = "El campo es obligatorio")
    @ValidIdentification()
    private String identificationNumber;

    @NotBlank(message = "El campo es obligatorio")
    @Size(max = 60, message = "El nombre completo no debe superar los 60 caracteres")
    private String fullName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Coloque un email valido")
    private String email;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "Solo se permiten números")
    @Pattern(regexp = "^(09\\d{8})$", message = "El número de celular debe empezar con 09 y tener 10 dígitos")
    private String cellphone;
}
