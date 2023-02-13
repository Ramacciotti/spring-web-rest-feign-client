package com.ramacciotti.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <ul>
 *      <li>A anotação @SpringBootApplicationanotação equivale a usar @Configuration, @EnableAutoConfiguration, e @ComponentScan com seus atributos padrão</li>
 *      <br>
 *      <li>A anotação @EnableJpaRepositories irá procurar nos pacotes do projeto onde existe uma classe de repositório anotada com @Repository</li>
 * </ul>
 */
@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
