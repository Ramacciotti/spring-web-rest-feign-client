package com.ramacciotti.company.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramacciotti.company.domain.dto.EmployeeRequestDTO;
import com.ramacciotti.company.domain.dto.EmployeeResponseDTO;
import com.ramacciotti.company.adapter.outbound.persistence.entity.Employee;
import com.ramacciotti.company.adapter.outbound.persistence.EmployeePersistence;
import com.ramacciotti.company.domain.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:config/application-test.properties")
public class EmployeeServiceTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeePersistence employeePersistence;


    @Test
    public void testPostEmployee() throws IOException {

       // 1) Simulates what the user will send in the request
        EmployeeRequestDTO employeeRequestDTO = getJsonFileAsObject("json/employee_request_1.json", EmployeeRequestDTO.class);

        // 2) Simulates what the repository will respond
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(null);

        // 3) Get the result of the method being tested
        EmployeeResponseDTO employeeResponseDTO = employeeService.postEmployee(employeeRequestDTO);

       // 4) Check how many times the method was called in the repository
        verify(employeePersistence, times(1)).findEmployeeByEmail(any());

        // 5) Check other fields
        Assertions.assertEquals("employee1@gmail.com", employeeResponseDTO.getEmail());
        Assertions.assertEquals("employee1", employeeResponseDTO.getName());
        Assertions.assertEquals(30, employeeResponseDTO.getAge());
        Assertions.assertEquals(LocalDate.now(), employeeResponseDTO.getHiringDate());

    }

    @Test
    public void testDoNotPostEmployeeIfEmailIsDuplicated() throws IOException {

       // 1) Simulates what the user will send in the request
        EmployeeRequestDTO employeeRequestDTO = getJsonFileAsObject("json/employee_request_1.json", EmployeeRequestDTO.class);

        // 1) Mockar repositório que irá gerar um exception
        when(employeePersistence.findEmployeeByEmail("employee1@gmail.com")).thenReturn(buildEmployeeEntity());

        // 2) Captura a exception que será gerada no service
        RuntimeException actual = Assertions.assertThrows(RuntimeException.class, () -> {
            employeeService.postEmployee(employeeRequestDTO);
        });

        // 3) Verifica mensagem de erro
        Assertions.assertEquals(actual.getMessage(), "email_already_registered");

    }


    @Test
    public void testPostEmployees() throws IOException {

        // 1) Simula a lista que o que o user mandará na request
        List<EmployeeRequestDTO> employeesRequest = buildEmployeesRequest();

        // 2) Simulates what the repository will respond
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(null);

        // 3) Pegar resultado do método do serviço que esta sendo testado
        List<EmployeeResponseDTO> employeesResponse = employeeService.postEmployees(employeesRequest);

       // 4) Check how many times the method was called in the repository
        verify(employeePersistence, times(2)).findEmployeeByEmail(any());

        // 5) Check other fields
        Assertions.assertEquals("employee1@gmail.com", employeesResponse.get(0).getEmail());
        Assertions.assertEquals("employee1", employeesResponse.get(0).getName());
        Assertions.assertEquals(30, employeesResponse.get(0).getAge());
        Assertions.assertEquals(LocalDate.now(), employeesResponse.get(0).getHiringDate());

        Assertions.assertEquals("employee2@gmail.com", employeesResponse.get(1).getEmail());
        Assertions.assertEquals("employee2", employeesResponse.get(1).getName());
        Assertions.assertEquals(32, employeesResponse.get(1).getAge());
        Assertions.assertEquals(LocalDate.now(), employeesResponse.get(1).getHiringDate());

    }

    @Test
    public void testDoNotPostEmployeesIfOneEmailIsDuplicated() throws IOException {

        // 1) Mockar repositório que irá gerar um exception
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(buildEmployeeEntity());

        // 2) Simula o que o user mandará na request
        List<EmployeeRequestDTO> employeesRequest = buildEmployeesRequest();

        // 3) Captura a exception que será gerada no service
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            employeeService.postEmployees(employeesRequest);
        });

        // 3) Verifica mensagem de erro
        Assertions.assertEquals(exception.getMessage(), "email_already_registered");

    }


    @Test
    public void testGetEmployee() throws IOException {

        // 1) Simula o que o repositório responderá
        Employee employee1 = getJsonFileAsObject("json/employee_entity_1.json", Employee.class);
        when(employeePersistence.findEmployeeByEmail("employee1@gmail.com")).thenReturn(employee1);

        // 2) Pegar resultado do método do serviço que esta sendo testado
        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployee("employee1@gmail.com");

        // 3) Verifica quantas vezes o método foi chamado no repositório
        verify(employeePersistence, times(1)).findEmployeeByEmail("employee1@gmail.com");

        // 4) Verifica outros campos
        Assertions.assertEquals("employee1@gmail.com", employeeResponseDTO.getEmail());
        Assertions.assertEquals("employee1", employeeResponseDTO.getName());
        Assertions.assertEquals(30, employeeResponseDTO.getAge());
        Assertions.assertEquals(generateDate("03/12/2022"), employeeResponseDTO.getHiringDate());

    }

    @Test
    public void testDoNotGetEmployeeIfEmailIsNotFound() {

        // 1) Mockar a entidade que o repositório deve retornar
        when(employeePersistence.findEmployeeByEmail("employee1@gmail.com")).thenReturn(null);

        // 2) Captura a exception que será gerada no service
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            employeeService.getEmployee("employee1@gmail.com");
        });

        // 3) Verifica mensagem de erro
        Assertions.assertEquals("employee_not_found", exception.getMessage());

    }


    @Test
    public void testGetEmployees() throws IOException {

        // 1) Simula o que o repositório responderá
        List<Employee> employeeEntities = buildEmployeeEntities();
        when(employeePersistence.findAll()).thenReturn(employeeEntities);

        // 2) Pegar resultado do método do serviço que esta sendo testado
        List<EmployeeResponseDTO> employeesResponse = employeeService.getEmployees();

        // 3) Verifica quantas vezes o método foi chamado no repositório
        verify(employeePersistence, times(1)).findAll();

        // 4) Verifica outros campos
        Assertions.assertEquals("employee1@gmail.com", employeesResponse.get(0).getEmail());
        Assertions.assertEquals("employee1", employeesResponse.get(0).getName());
        Assertions.assertEquals(30, employeesResponse.get(0).getAge());
        Assertions.assertEquals(generateDate("03/12/2022"), employeesResponse.get(0).getHiringDate());

        Assertions.assertEquals("employee2@gmail.com", employeesResponse.get(1).getEmail());
        Assertions.assertEquals("employee2", employeesResponse.get(1).getName());
        Assertions.assertEquals(32, employeesResponse.get(1).getAge());
        Assertions.assertEquals(generateDate("03/12/2023"), employeesResponse.get(1).getHiringDate());

    }

    @Test
    public void testDoNotGetEmployeesIfThereIsNone() {

        // 1) Mockar a entidade que o repositório deve retornar
        when(employeePersistence.findAll()).thenReturn(new ArrayList<>());

        // 2) Pegar resultado do método do serviço que esta sendo testado
        List<EmployeeResponseDTO> employeesDTO = employeeService.getEmployees();

         // 4) Verifications
        Assertions.assertEquals(new ArrayList<>(), employeesDTO);

    }


    @Test
    public void testUpdateEmployee() throws IOException {

       // 1) Simulates what the user will send in the request
        EmployeeRequestDTO employeeRequestDTO = getJsonFileAsObject("json/update_employee_request.json", EmployeeRequestDTO.class);

        // 2) Simulates what the repository will respond
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(buildEmployeeEntity());

        // 3) Get the result of the method being tested
        EmployeeResponseDTO employeeResponseDTO = employeeService.putEmployee(employeeRequestDTO, "employee1@email.com");

       // 4) Check how many times the method was called in the repository
        verify(employeePersistence, times(1)).findEmployeeByEmail(any());

        // 5) Check other fields
        Assertions.assertEquals("updated_employee1@gmail.com", employeeResponseDTO.getEmail());
        Assertions.assertEquals("employee1", employeeResponseDTO.getName());
        Assertions.assertEquals(30, employeeResponseDTO.getAge());
        Assertions.assertEquals(generateDate("03/12/2022"), employeeResponseDTO.getHiringDate());

    }

    @Test
    public void testDoNotUpdateEmployeeIfEmailWasNotRegistered() throws IOException {

       // 1) Simulates what the user will send in the request
        EmployeeRequestDTO employeeRequestDTO = getJsonFileAsObject("json/update_employee_request.json", EmployeeRequestDTO.class);

        // 2) Simulates what the repository will respond que irá gerar um exception
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(null);

        // 3) Captura a exception que será gerada no service
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            employeeService.putEmployee(employeeRequestDTO, any());
        });

        // 4) Verifica mensagem de erro
        Assertions.assertEquals("employee_not_found", exception.getMessage());

    }


    @Test
    public void testDeleteEmployee() throws IOException {

        // 1) Mockar repositório
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(buildEmployeeEntity());

        // 2) Realiza chamada
        employeeService.deleteEmployee(any());

        // 3) Verifica quantas vezes o método foi chamado no repositório
        verify(employeePersistence, times(1)).findEmployeeByEmail(any());
        verify(employeePersistence, times(1)).deleteById(any());

    }

    @Test
    public void testDoNotDeleteEmployeeIfEmailWasNotRegistered() {

        // 1) Mockar repositório
        when(employeePersistence.findEmployeeByEmail(any())).thenReturn(null);

        // 2) Captura a exception que será gerada no service
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            employeeService.deleteEmployee(any());
        });

        // 3) Verifica mensagem de erro
        Assertions.assertEquals("employee_not_found", exception.getMessage());

    }


    public Employee buildEmployeeEntity() throws IOException {

        return getJsonFileAsObject("json/employee_entity_1.json", Employee.class);

    }

    public List<Employee> buildEmployeeEntities() throws IOException {

        Employee employee1 = getJsonFileAsObject("json/employee_entity_1.json", Employee.class);

        Employee employee2 = getJsonFileAsObject("json/employee_entity_2.json", Employee.class);

        return List.of(employee1, employee2);

    }

    private List<EmployeeRequestDTO> buildEmployeesRequest() throws IOException {

        EmployeeRequestDTO employee1 = getJsonFileAsObject("json/employee_entity_1.json", EmployeeRequestDTO.class);

        EmployeeRequestDTO employee2 = getJsonFileAsObject("json/employee_entity_2.json", EmployeeRequestDTO.class);

        return List.of(employee1, employee2);

    }

    private static LocalDate generateDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);

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

