package com.ramacciotti.company.infrastructure.config.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("prod")
@OpenAPIDefinition
public class OpenApiConfig {

    @Value("${info.app.name}")
    private String name;

    @Value("${info.app.version}")
    private String version;

    @Value("${info.app.description}")
    private String description;

    @Value("${info.app.author}")
    private String author;

    @Value("${info.app.repository}")
    private String repository;

    @Value("${info.app.linkedin}")
    private String linkedin;

    @Value("${info.app.content}")
    private String content;

    @Bean
    public OpenAPI configure() {
        return new OpenAPI()
                .info(info())
                .servers(servers())
                .tags(tags());
    }

    private Info info() {
        return new Info()
                .title(name)
                .version(version)
                .description(buildDescription());
    }

    private List<Tag> tags() {

        Tag employeeController = new Tag();
        employeeController.setName("Employee Controller");

        return List.of(employeeController);

    }

    private List<Server> servers() {

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");

        return List.of(localServer);

    }

    private String buildDescription() {
        return String.format(
                "<b>Description:</b> %s <br/><br/>" +
                        "<b>Content:</b> %s <br/><br/>" +
                        "<b>Author:</b> %s <br/><br/>" +
                        "<a href=" + repository + ">Github Repository</a><br/><br/>" +
                        "<a href=" + linkedin + ">Linkedin Profile</a><br/>",
                description, content, author);
    }

}
