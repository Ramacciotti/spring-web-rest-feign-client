package com.ramacciotti.external.adapter.inbound.rest;

import com.ramacciotti.external.domain.dto.externalApi.UserDTO;
import com.ramacciotti.external.domain.ports.inbound.ExternalApiUseCase;
import com.ramacciotti.external.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <ul>
 *      <li>A Controller faz o "primeiro contato" com as requisições, enviando a camada de Services apenas as informações relevantes para completar a requisição.</li>
 *      <br>
 *      <li>Esta classe deve realizar apenas operações relacionadas a Request e Response (HTTP), ou seja, não deve possuir "conhecimento" sobre regras de negócios, ou acesso ao DB</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/users")
    @Operation(description = "Get  a list of Users from external API")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception {
        log.info(">> getAllUsers()");
        List<UserDTO> result = userService.fetchUsers();
        log.info("<< getAllUsers()");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
