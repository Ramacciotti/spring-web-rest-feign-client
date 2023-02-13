package com.ramacciotti.company.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
public class EmployeeResponseDTO {

    private String name;

    private String email;

    private Integer age;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate hiringDate;

}
