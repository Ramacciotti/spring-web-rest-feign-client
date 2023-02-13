package com.ramacciotti.external.domain.service;

import com.ramacciotti.external.adapter.outbound.persistence.entity.User;
import com.ramacciotti.external.adapter.outbound.persistence.repository.UserRepository;
import com.ramacciotti.external.domain.dto.externalApi.UserDTO;
import com.ramacciotti.external.domain.ports.inbound.ExternalApiUseCase;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service is responsible for storing and abstracting the business rules, so that the Model layer is "light" and objective, and is also responsible for accessing the data, validating whether the information received from the Controllers layer is sufficient to complete the request.
 * Good habits:
 * <br>
 * 1) Not having any "knowledge" about the Model layer (EX: Query SQL)
 * <br>
 * 2) Do not receive anything related to HTTP (Request or Response)
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private ExternalApiUseCase externalApiUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CompanyService companyService;

    public List<UserDTO> fetchUsers() throws Exception {

        List<UserDTO> userListDTO;

        try {

            userListDTO = externalApiUseCase.fetchUsers();

            log.info("** Data fetched successfully: {}", userListDTO.toString());

            saveAllUsers(userListDTO);

        } catch (FeignException error) {

            log.error("## Ops! Could not fetch data from external API: {}", error.getMessage());

            log.error(error.getMessage());

            throw new Exception("could_not_fetch_data_from_external_api");

        }

        return userListDTO;

    }

    @Transactional
    public void saveAllUsers(List<UserDTO> userListDTO) {

        for (UserDTO userDTO : userListDTO) {

            log.info("--------------------------------------------------------------------------------------");

            log.info("Creating user object...");

            User user = new User()
                    .withEmail(userDTO.getEmail())
                    .withName(userDTO.getName())
                    .withPhone(userDTO.getPhone())
                    .withUsername(userDTO.getUsername())
                    .withWebsite(userDTO.getWebsite());

            user = userRepository.save(user);

            addressService.save(userDTO.getAddress(), user);

            companyService.save(userDTO.getCompany(), user);

        }

    }

}
