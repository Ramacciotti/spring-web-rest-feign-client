package com.ramacciotti.external.infrastructure.builder;

import com.ramacciotti.external.adapter.outbound.persistence.entity.User;
import com.ramacciotti.external.domain.dto.externalApi.UserDTO;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

public class ResponseBuilder {

    public static UserDTO buildSingleResponse(User entity) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, UserDTO.class);

    }

    public static List<UserDTO> buildListResponse(List<User> entities) {

        ModelMapper modelMapper = new ModelMapper();
        return Arrays.asList(modelMapper.map(entities, UserDTO[].class));

    }

}
