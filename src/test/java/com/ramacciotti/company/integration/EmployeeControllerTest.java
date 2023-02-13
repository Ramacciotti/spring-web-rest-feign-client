package com.ramacciotti.company.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.adapter.outbound.persistence.EmployeePersistence;
import com.ramacciotti.company.domain.service.EmployeeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config/application-test.properties")
class EmployeeControllerTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    EmployeePersistence employeePersistence;

    @Test
    public void testPostEmployee() throws Exception {

       // 1) Simulates what the user will send in the request
        String employeeRequest = getJsonFileAsString("json/employee_request_1.json");

        // 2) Simulates what the service will respond
        EmployeeResponseDTO employeeResponseDTO = getJsonFileAsObject("json/employee_response_1.json", EmployeeResponseDTO.class);
        when(employeeService.postEmployee(any())).thenReturn(employeeResponseDTO);

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

    @Test
    public void testPostEmployees() throws Exception {

       // 1) Simulates what the user will send in the request
        String employeeRequest = getJsonFileAsString("json/employees_request.json");

        // 2) Simulates what the service will respond
        when(employeeService.postEmployees(any())).thenReturn(buildEmployeesResponse());

        // 3) Execute the request
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/employees")
                        .content(employeeRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var actualObject = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<EmployeeResponseDTO>>() {
        });
        var expectedObject = buildEmployeesResponse();

         // 4) Verifications
        Assertions.assertEquals(expectedObject.get(0).getName(), actualObject.get(0).getName());
        Assertions.assertEquals(expectedObject.get(0).getEmail(), actualObject.get(0).getEmail());
        Assertions.assertEquals(expectedObject.get(0).getAge(), actualObject.get(0).getAge());

    }


    @Test
    public void testGetEmployeeByEmail() throws Exception {

        // 1) Simula o que a service responderá
        EmployeeResponseDTO employeeResponseDTO = getJsonFileAsObject("json/employee_response_1.json", EmployeeResponseDTO.class);
        when(employeeService.getEmployee("employee1@gmail.com")).thenReturn(employeeResponseDTO);

        // 2) Realiza a requisição
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/employee/email/employee1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var actualObject = mapper.readValue(response.getResponse().getContentAsString(), EmployeeResponseDTO.class);
        var expectedObject = getJsonFileAsObject("json/employee_response_1.json", EmployeeResponseDTO.class);

         // 3) Verifications
        Assertions.assertEquals(expectedObject.getName(), actualObject.getName());
        Assertions.assertEquals(expectedObject.getEmail(), actualObject.getEmail());
        Assertions.assertEquals(expectedObject.getAge(), actualObject.getAge());

    }

    @Test
    public void testGetAllEmployees() throws Exception {

        // 1) Simula o que a service responderá
        when(employeeService.getEmployees()).thenReturn(buildEmployeesResponse());

        // 2) Realiza a requisição
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var actualObject = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {
        });
        var expectedObject = buildEmployeesResponse();

         // 3) Verifications
        Assertions.assertEquals(expectedObject.get(0).getName(), actualObject.get(0).getName());
        Assertions.assertEquals(expectedObject.get(0).getEmail(), actualObject.get(0).getEmail());
        Assertions.assertEquals(expectedObject.get(0).getAge(), actualObject.get(0).getAge());
        Assertions.assertEquals(expectedObject.get(1).getEmail(), actualObject.get(1).getEmail());
        Assertions.assertEquals(expectedObject.get(1).getEmail(), actualObject.get(1).getEmail());
        Assertions.assertEquals(expectedObject.get(1).getAge(), actualObject.get(1).getAge());

    }


    @Test
    public void testUpdateEmployee() throws Exception {

       // 1) Simulates what the user will send in the request
        String employeeRequest = getJsonFileAsString("json/update_employee_request.json");

        // 2) Simulates what the service will respond
        EmployeeResponseDTO employeeResponseDTO = getJsonFileAsObject("json/update_employee_response.json", EmployeeResponseDTO.class);
        when(employeeService.putEmployee(any(), any())).thenReturn(employeeResponseDTO);

        String emailToBeReplaced = "employee1@gmail.com";

        // 3) Execute the request
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/employee/email/" + emailToBeReplaced)
                        .content(employeeRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var actualObject = mapper.readValue(response.getResponse().getContentAsString(), EmployeeResponseDTO.class);
        var expectedObject = getJsonFileAsObject("json/update_employee_response.json", EmployeeResponseDTO.class);

         // 4) Verifications
        Assertions.assertEquals(expectedObject.getName(), actualObject.getName());
        Assertions.assertEquals(expectedObject.getEmail(), actualObject.getEmail());
        Assertions.assertEquals(expectedObject.getAge(), actualObject.getAge());

    }


    @Test
    public void testDeleteEmployee() throws Exception {

        // 1) Execute the request
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/employee/email/employee1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }


    private List<EmployeeResponseDTO> buildEmployeesResponse() throws IOException {

        EmployeeResponseDTO savedEmployee1 = getJsonFileAsObject("json/employee_response_1.json", EmployeeResponseDTO.class);
        EmployeeResponseDTO savedEmployee2 = getJsonFileAsObject("json/employee_response_2.json", EmployeeResponseDTO.class);
        List<EmployeeResponseDTO> employeeList = new ArrayList<>();
        employeeList.add(savedEmployee1);
        employeeList.add(savedEmployee2);
        return employeeList;

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