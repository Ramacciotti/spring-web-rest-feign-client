package com.ramacciotti.company.infrastructure.builder;

import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

public class ResponseBuilder {

    public static EmployeeResponseDTO buildSingleResponse(Employee entity) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, EmployeeResponseDTO.class);

    }

    public static List<EmployeeResponseDTO> buildListResponse(List<Employee> entities) {

        ModelMapper modelMapper = new ModelMapper();
        return Arrays.asList(modelMapper.map(entities, EmployeeResponseDTO[].class));

    }

}
