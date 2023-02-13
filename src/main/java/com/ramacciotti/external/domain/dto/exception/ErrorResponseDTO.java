package com.ramacciotti.external.domain.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.http.HttpStatus;

/**
 * DTO is used for transferring data between layers. For example, we can have a DTO that takes data from a registration form (User interface layer) and transports the data to a Controller (application layer).
 * <br>
 * 1) If you use a MODEL, which could have a password, it will be visible in the return of the request. With DTO you can remove fields you don't want to show
 * <br>
 *2) With a DTO you can add additional settings to remove null spaces, for example
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@With
public class ErrorResponseDTO {

    private HttpStatus status;

    private String message;

}
