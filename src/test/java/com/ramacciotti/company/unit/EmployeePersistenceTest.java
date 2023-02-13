package com.ramacciotti.company.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.domain.ports.outbound.EmployeePersistenceUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config/application-test.properties")
class EmployeePersistenceTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeePersistenceUseCase employeePersistenceUseCase;

    @Test
    public void testPostEmployee() throws Exception {

       // 1) Simulates what the user will send in the request
        String employeeRequest = getJsonFileAsString("json/employee_request_1.json");

        // 2) Simulates what the repository will respond
        doNothing().when(employeePersistenceUseCase).save(any());

        // 3) Execute the request
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/employee")
                        .content(employeeRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var actualObject = mapper.readValue(response.getResponse().getContentAsString(), EmployeeResponseDTO.class);
        var expectedObject = getJsonFileAsObject("json/employee_response_1.json", EmployeeResponseDTO.class);

         // 4) Verifications
        Assertions.assertEquals(expectedObject.getName(), actualObject.getName());
        Assertions.assertEquals(expectedObject.getEmail(), actualObject.getEmail());
        Assertions.assertEquals(expectedObject.getAge(), actualObject.getAge());

    }


    private String getJsonFileAsString(String filename) throws IOException {
        File file = new File(((URL) Objects.requireNonNull(classLoader.getResource(filename))).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }

    private <T> T getJsonFileAsObject(String filename, Class<T> typeKey) throws IOException {
        var json = getJsonFileAsString(filename);
        return mapper.readValue(json, typeKey);
    }

}