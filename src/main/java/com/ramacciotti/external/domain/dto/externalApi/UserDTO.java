package com.ramacciotti.external.domain.dto.externalApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private String username;

    private String email;

    private String phone;

    private String website;

    private AddressDTO address;

    private CompanyDTO company;

}
