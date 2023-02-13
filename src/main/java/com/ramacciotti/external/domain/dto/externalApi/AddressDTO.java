package com.ramacciotti.external.domain.dto.externalApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String street;

    private String suite;

    private String city;

    private String zipcode;

    private GeoDTO geo;

}
