package com.ramacciotti.company.domain.service;

import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.domain.dto.EmployeeRequestDTO;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.domain.ports.inbound.EmployeeServiceUseCase;
import com.ramacciotti.company.domain.ports.outbound.EmployeePersistenceUseCase;
import com.ramacciotti.company.infrastructure.builder.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class EmployeeService implements EmployeeServiceUseCase {

    @Autowired
    EmployeePersistenceUseCase employeePersistenceUseCase;


    @Override
    public EmployeeResponseDTO postEmployee(EmployeeRequestDTO employeeRequestDTO) {

        Employee duplicatedEmployee = employeePersistenceUseCase.findEmployeeByEmail(employeeRequestDTO.getEmail());

        if (duplicatedEmployee == null) {

            Employee employee = new Employee()
                    .withName(employeeRequestDTO.getName())
                    .withEmail(employeeRequestDTO.getEmail())
                    .withAge(employeeRequestDTO.getAge())
                    .withPassword(employeeRequestDTO.getPassword())
                    .withHiringDate(LocalDate.now());

            employeePersistenceUseCase.save(employee);

            return ResponseBuilder.buildSingleResponse(employee);

        } else {

            throw new RuntimeException("email_already_registered");

        }

    }


    @Override
    public List<EmployeeResponseDTO> postEmployees(List<EmployeeRequestDTO> employeesRequest) {

        try {

            List<EmployeeResponseDTO> result = new ArrayList<>();

            for (EmployeeRequestDTO employeeRequestDTO : employeesRequest) {
                result.add(postEmployee(employeeRequestDTO));
            }

            return result;

        } catch (RuntimeException error) {

            throw new RuntimeException(error.getMessage());

        }

    }


    @Override
    public EmployeeResponseDTO getEmployee(String email) {

        Employee entity = employeePersistenceUseCase.findEmployeeByEmail(email);

        if (entity != null) {

            return ResponseBuilder.buildSingleResponse(entity);

        } else {

            throw new NotFoundException("employee_not_found");

        }

    }

    @Override
    public List<EmployeeResponseDTO> getEmployees() {

        List<Employee> entities = employeePersistenceUseCase.findAll();

        return ResponseBuilder.buildListResponse(entities);

    }


    @Override
    public EmployeeResponseDTO putEmployee(EmployeeRequestDTO employeeRequestDTO, String emailToBeReplaced) {

        Employee entity = employeePersistenceUseCase.findEmployeeByEmail(emailToBeReplaced);

        if (entity != null) {

            entity = updateFields(entity, employeeRequestDTO);

            employeePersistenceUseCase.save(entity);

            return ResponseBuilder.buildSingleResponse(entity);

        } else {

            throw new NotFoundException("employee_not_found");

        }

    }


    @Override
    public void deleteEmployee(String email) {

        Employee employee = employeePersistenceUseCase.findEmployeeByEmail(email);

        if (employee != null) {

            employeePersistenceUseCase.deleteById(employee.getId());

        } else {

            throw new NotFoundException("employee_not_found");

        }

    }


    private Employee updateFields(Employee entity, EmployeeRequestDTO employeeRequestDTO) {

        if (employeeRequestDTO.getName() != null) {
            entity.setName(employeeRequestDTO.getName());
        } else {
            entity.setName(entity.getName());
        }

        if (employeeRequestDTO.getEmail() != null) {
            entity.setEmail(employeeRequestDTO.getEmail());
        } else {
            entity.setEmail(entity.getEmail());
        }

        if (employeeRequestDTO.getAge() != null) {
            entity.setAge(employeeRequestDTO.getAge());
        } else {
            entity.setAge(entity.getAge());
        }

        if (employeeRequestDTO.getPassword() != null) {
            entity.setPassword(employeeRequestDTO.getPassword());
        } else {
            entity.setPassword(entity.getPassword());
        }

        return entity;

    }


}
