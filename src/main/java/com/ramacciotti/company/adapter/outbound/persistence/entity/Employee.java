package com.ramacciotti.company.adapter.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class is responsible for processing requests and generating responses, that is: it analyzes the input data obtained by the request, passes this data to the service layer and returns a response.
 */
@With
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // conversion of java objects to bytes to be saved in the database

    @Id // UUID type identifier
    @GeneratedValue(strategy = GenerationType.AUTO) // The ID will be generated automatically
    private UUID id; // UUID is a unique identifier and not at risk of conflict within the architecture

    @Column(nullable = false, length = 20) // field that cannot be null
    private String name;

    @Column(nullable = false, unique = true, length = 40) // field that cannot be null, has to be a unique field
    private String email;

    @Column(nullable = false,length = 2)
    private Integer age;

    @Column(nullable = false, length = 6) // field that cannot be null, has to be a unique field
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate hiringDate;

}
