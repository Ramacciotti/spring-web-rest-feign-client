## Consumming an external API

**Description**: An application rarely “lives alone” and at some point the need to integrate it with other applications
may arise. Nowadays many of the integrations use the REST architectural model, just like this project, whose objective
is to fetch a list of users and save it on a local database.<br>

**Tags**: JAVA 17, SpringBoot, OpenApi 3.0, Junit 5.0, FeignClient<br>

## How to run the Project locally?

Fill in the necessary variables for the database connection in the pom.xml. If you don't want to put by variables
IDE environment, you can fill it directly as in the example below:

     * spring.datasource.driverClassName=org.postgresql.Driver
     * spring.datasource.url=jdbc:postgresql://localhost:5432/database_company
     * spring.datasource.username=your_bank_username
     * spring.datasource.password=your_bank_password

## Tutorials

1) How to install PostgreSQL and
   pgAdmin: [tutorial](https://www.youtube.com/watch?v=L_2l8XTCPAE&ab_channel=HashtagPrograma%C3%A7%C3%A3o);
2) How to create a new user in
   PostgreSQL: [tutorial](https://www.youtube.com/watch?v=oEe2mRF-irw&t=261s&ab_channel=CanaldoCris);