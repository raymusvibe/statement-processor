# Rabobank Statement Validator

The Rabobank statement validator provides an API to validate statements records uploaded in CSV and XML format.
The validation results are returned in JSON or CSV format through the two API endpoints. The JSON
validation response can be used by other applications, while the CSV report download allows business users
to open the validation report in a tool like Excel.

# Requirements

- [Java 17](https://jdk.java.net/17/),
- [Maven 3](https://maven.apache.org/download.cgi),
- [SonarQube](https://www.sonarsource.com/) (if you want to run code analysis)

## How to run maven-spotless for code formatting (in the terminal)

```./mvnw spotless:apply```

## How to build the project

```./mvnw package```

## How to run tests

```./mvnw test```

## How to run SonarQube analysis

```
./mvnw clean verify sonar:sonar \
-Dsonar.projectKey=Statement-Validator \
-Dsonar.projectName='Statement-Validator' \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token={security_token}
```

## How to run the application with Java

```java -jar validator-0.0.1-SNAPSHOT.jar```

## Run the application with maven wrapper

```./mvnw spring-boot:run```

## Docker

The application can also be built and run using [docker](https://www.docker.com/).
You'll need to install and run docker on localhost to proceed with the following docker commands.

### Building the application with docker

```docker build -t statement-validator-api .```

### Running the application with docker

```docker run -p 8080:8080 -t statement-validator-api```

### Swagger-UI:

Once the application is running, a [swagger interface](http://localhost:8080/swagger-ui/index.htm) will become available.
One of the endpoints produces the report in JSON format (/api/v1/report), and the other allows users to download the
report in CSV format (/api/v1/report/csv).

## Technologies used

- Java 17,
- Maven 3,
- Spring Boot 3,
- Lombok,
- Swagger 3,
- Spotless,
- Jacoco,
- JUnit 5,
- Docker
- OpenCSV
- JAXB

## Architectural choices

- Chose Spring boot for its rapid application development properties
- Swagger integrates easily with Spring boot to create an easy-to-use interface to interact with the API
- OpenCSV and JAXB are competent libraries for parsing and validating CSV and XML data

## Areas for additional development

- Add CI/CD support
- Add more tests
- Add front-end to interact with the API
- Scan all incoming files for security threats

## Code coverage and results of static code analysis

- Jacoco reports that 100% of classes are covered by tests, and 93% of lines
- SonarQube reports no major issues or code smells

