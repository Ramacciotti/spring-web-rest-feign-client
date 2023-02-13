package com.ramacciotti.external.domain.service;

import com.ramacciotti.external.adapter.outbound.persistence.entity.Address;
import com.ramacciotti.external.adapter.outbound.persistence.entity.User;
import com.ramacciotti.external.adapter.outbound.persistence.repository.AddressRepository;
import com.ramacciotti.external.domain.dto.externalApi.AddressDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GeoService geoService;

    @Transactional
    public void save(AddressDTO addressDTO, User user) {

        log.info("Creating address object...");

        Address address = new Address()
                .withCity(addressDTO.getCity())
                .withSuite(addressDTO.getSuite())
                .withStreet(addressDTO.getStreet())
                .withZipcode(addressDTO.getZipcode())
                .withUser(user);

        address = addressRepository.save(address);

        geoService.save(addressDTO.getGeo(), address);

    }

}
