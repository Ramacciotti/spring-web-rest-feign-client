## Information about the Project

**Description**: Project that uses hexagonal architecture, boils down to a crud application in
JAVA that handles data from persons registered in a PostgreSQL database.<br>
**Language**: JAVA, 17<br>
**Framework**: SpringBoot<br>
**Library**: OpenApi 3.0<br>
**Testing**: Junit 5.0<br>

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