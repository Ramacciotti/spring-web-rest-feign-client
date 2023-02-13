package com.ramacciotti.external.domain.ports.inbound;

import com.ramacciotti.external.domain.dto.externalApi.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${external.api.base.url}", value = "external-api")
public interface ExternalApiUseCase {

    @GetMapping(path = "/users")
    List<UserDTO> fetchUsers() throws Exception;

}