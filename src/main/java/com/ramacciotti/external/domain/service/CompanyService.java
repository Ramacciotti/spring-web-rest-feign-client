package com.ramacciotti.external.domain.service;

import com.ramacciotti.external.adapter.outbound.persistence.entity.Company;
import com.ramacciotti.external.adapter.outbound.persistence.entity.User;
import com.ramacciotti.external.adapter.outbound.persistence.repository.CompanyRepository;
import com.ramacciotti.external.domain.dto.externalApi.CompanyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public void save(CompanyDTO companyDTO, User user) {

        log.info("Creating company object...");

        Company company = new Company()
                .withBs(companyDTO.getBs())
                .withName(companyDTO.getName())
                .withCatchPhrase(companyDTO.getCatchPhrase())
                .withUser(user);

        companyRepository.save(company);

    }

}
