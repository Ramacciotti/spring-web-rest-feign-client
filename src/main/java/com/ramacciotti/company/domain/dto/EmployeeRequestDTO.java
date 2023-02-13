package com.ramacciotti.company.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO is used for transferring data between layers. For example, we can have a DTO that takes data from a registration form (User interface layer) and transports the data to a Controller (application layer).
 * <br>
 * 1) If you use a MODEL, which could have a password, it will be visible in the return of the request. With DTO you can remove fields you don't want to show
 * <br>
 *2) With a DTO you can add additional settings to remove null spaces, for example
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequestDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    @NotNull
    private Integer age;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

}
