package com.ramacciotti.company.adapter.outbound.persistence;

import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.adapter.outbound.persistence.repository.EmployeeRepository;
import com.ramacciotti.company.domain.ports.outbound.EmployeePersistenceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EmployeePersistence implements EmployeePersistenceUseCase {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

}
