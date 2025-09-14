# Backend Application -Soninkrala

## Project Overview
This part is the backend service for the "Soninkrala" project, which helps people to learn the soninke dialect. The backend is built using Spring Boot and Java, and it connects to a PostgreSQL database to store member information.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Java 21+
- Maven 3.6+
- PostgreSQL 12+
- A Java IDE (IntelliJ IDEA, Eclipse, etc.)

## Installation

### 1. Clone the Repository

Clone the Git repository to your local machine using the following command:

```bash
https://github.com/Saladin88/SoninKrala.git
```

### 2. Database Configuration

Create a PostgreSQL database and configure the connection details in the `application.properties` file:

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:XXXX/dt_database
spring.datasource.username=your-username
spring.datasource.password=your-password

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace `your-database-name`, `your-username`, and `your-password` with your PostgreSQL setup details.

### 3. Build the Project

Navigate to the project directory and run the following command to build the project and download the dependencies:

```bash
mvn clean install
```

### 4. Run the Application

Start the Spring Boot application by running the following command:

```bash
mvn spring-boot:run
```

The application will be accessible at:

```
http://localhost:8080
```

## API Endpoints

The API exposes the following endpoints folowwing the role you have to have access to resources:

### Customers

- **POST /form**: Creates a new customer.

## Project Structure

The project is organized as follows:

```
src/
│
├── main/
│   ├── java/co/simplon/soninkrala/
│   │   ├── controllers/      # REST controllers for handling HTTP requests
│   │   ├── dtos/             # Data Transfer Objects for carrying data
│   │   ├── entities/         # JPA entities mapped to database tables
│   │   ├── enums/            # Enums for constant types used in entities
│   │   ├── repositories/     # Interfaces for data access via JPA
│   │   └── services/         # Services for business logic
|   |   └── servicesImpl/         # Services for business logic
│   └── resources/
│       ├── application.properties   # Application configuration file
│       └── data.sql          # (Optional) SQL script for seeding test data
│
└── test/                     # Unit and integration tests
```
