package com.ramacciotti.external.domain.service;

import com.ramacciotti.external.adapter.outbound.persistence.entity.Address;
import com.ramacciotti.external.adapter.outbound.persistence.entity.Geo;
import com.ramacciotti.external.adapter.outbound.persistence.repository.GeoRepository;
import com.ramacciotti.external.domain.dto.externalApi.GeoDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoService {

    @Autowired
    private GeoRepository geoRepository;

    @Transactional
    public void save(GeoDTO geoDTO, Address address) {

        log.info("Creating geo object...");

        Geo geo = new Geo()
                .withLat(geoDTO.getLat())
                .withLng(geoDTO.getLng()).
                withAddress(address);

        geoRepository.save(geo);

    }

}
