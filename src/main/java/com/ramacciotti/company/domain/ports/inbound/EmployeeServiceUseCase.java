package com.ramacciotti.company.domain.ports.inbound;

import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.domain.dto.EmployeeRequestDTO;

import java.util.List;

/**
 * The interface is like a contract, through it we can specify which methods the classes will have to implement.
 * It is an efficient way to protect data manipulated within the class, in addition to determining where this class can be manipulated.
 * We use the most restrictive access level, private, that makes sense for a particular member.
 * Thanks to encapsulation, we can see classes only for the services they are supposed to provide for those who use them. We do not visualize, in this case, how the service is implemented internally in the class.
 */
public interface EmployeeServiceUseCase {

    EmployeeResponseDTO postEmployee(EmployeeRequestDTO employeeRequestDTO);

    List<EmployeeResponseDTO> postEmployees(List<EmployeeRequestDTO> employeeRequestDTO) throws Exception;

    EmployeeResponseDTO getEmployee(String email) throws Exception;

    List<EmployeeResponseDTO> getEmployees();

    EmployeeResponseDTO putEmployee(EmployeeRequestDTO employeeRequestDTO, String email) throws Exception;

    void deleteEmployee(String email) throws Exception;

}
