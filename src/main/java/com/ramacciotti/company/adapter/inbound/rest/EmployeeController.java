package com.ramacciotti.company.adapter.inbound.rest;

import com.ramacciotti.company.domain.dto.EmployeeRequestDTO;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.domain.ports.inbound.EmployeeServiceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Tag(name = "Employee Controller")
public class EmployeeController {

    @Autowired
    EmployeeServiceUseCase employeeServiceUseCase;


    @PostMapping(path = "/employee")
    @Operation(description = "Save an employee data in the database")
    public ResponseEntity<EmployeeResponseDTO> postEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO result = employeeServiceUseCase.postEmployee(employeeRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(path = "/employees")
    @Operation(description = "Saves a list of employees in the database")
    public ResponseEntity<List<EmployeeResponseDTO>> postEmployees(@Valid @RequestBody List<EmployeeRequestDTO> employeesRequest) throws Exception {
        List<EmployeeResponseDTO> result = employeeServiceUseCase.postEmployees(employeesRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping(path = "/employee/email/{email}")
    @Operation(description = "Uses an email to locate an specific employee at the database")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByEmail(@PathVariable("email") String email) throws Exception {
        EmployeeResponseDTO result = employeeServiceUseCase.getEmployee(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/employees")
    @Operation(description = "Returns a list of employees registered in the database")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> result = employeeServiceUseCase.getEmployees();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping(path = "/employee/email/{email}")
    @Operation(description = "Uses an email to update an specific employee at the database")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO, @PathVariable("email") String emailToBeReplaced) throws Exception {
        EmployeeResponseDTO result = employeeServiceUseCase.putEmployee(employeeRequestDTO, emailToBeReplaced);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping(path = "/employee/email/{email}")
    @Operation(description = "Uses an email to delete an specific employee at the database")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("email") String email) throws Exception {
        employeeServiceUseCase.deleteEmployee(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
