package com.ramacciotti.company.domain.ports.outbound;

import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;

import java.util.List;
import java.util.UUID;

/**
 * The interface is like a contract, through it we can specify which methods the classes will have to implement.
 * It is an efficient way to protect data manipulated within the class, in addition to determining where this class can be manipulated.
 * We use the most restrictive access level, private, that makes sense for a particular member.
 * Thanks to encapsulation, we can see classes only for the services they are supposed to provide for those who use them. We do not visualize, in this case, how the service is implemented internally in the class.
 */
public interface EmployeePersistenceUseCase {

    void save(Employee employee);

    Employee findEmployeeByEmail(String email);

    List<Employee> findAll();

    void deleteById(UUID id);

}
