package com.ramacciotti.external.adapter.outbound.persistence.repository;

import com.ramacciotti.external.adapter.outbound.persistence.entity.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeoRepository extends JpaRepository<Geo, UUID> {
}
