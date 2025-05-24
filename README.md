# AMak Application

This application is a showcase of Domain-Driven Design (DDD) using Spring Boot.

## Architecture

The application is structured into the following layers:

- **Domain**: This layer contains the business logic of the application. It is responsible for defining the domain model, including the entities, value objects, and aggregate roots.
- **Application**: This layer contains the use cases of the application. It is responsible for orchestrating the interactions between the domain model and the infrastructure.
- **Infrastructure**: This layer contains the infrastructure-specific code of the application. It is responsible for providing the implementations of the interfaces defined in the application layer.
- **Shared**: This layer contains the shared code for the application. It is responsible for providing the utilities and constants used throughout the application.
- **Api**: This layer contains the API specific code of the application. It is responsible for providing the controllers that handle the requests and responses.

## API

The API is defined in the Api package. It provides the controllers that handle the requests and responses.

## Domain Model

The domain model is defined in the Domain package. It consists of the following entities:

## Application Use Cases

The application use cases are defined in the Application package. They are responsible for orchestrating the interactions between the domain model and the infrastructure.

## Infrastructure

The infrastructure is defined in the Infrastructure package. It provides the implementations of the interfaces defined in the application layer.

## Shared

The shared package contains the shared code for the application. It is responsible for providing the utilities and constants used throughout the application.

## Technologies Used

The application uses the following technologies:

- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- MapStruct
- Java 21
- MySQL 8
- Docker

## How to Run

To run the application, execute the following command:

```bash
./gradlew bootRun
```

To run the application with docker, execute the following command:

```bash
docker-compose up
```

## License

The application is licensed under the Apache License 2.0.
