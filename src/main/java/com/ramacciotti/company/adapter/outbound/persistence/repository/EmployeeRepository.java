package com.ramacciotti.company.adapter.outbound.persistence.repository;

import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findEmployeeByEmail(String email);

}