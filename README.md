# Custom Authentication & Mail Service

This is a Spring Boot application that provides a custom authentication and email verification service.

## Features

- User registration with email verification
- User login with JWT authentication
- Secure password handling
- Email sending for account verification

## Technologies Used

- Java 25
- Spring Boot 4.0.2
- Spring Security
- Spring Data JPA
- MySQL
- Flyway
- Spring Mail
- JJWT (JSON Web Token)
- Lombok
- MapStruct

## API Endpoints

### Authentication

- `POST /api/auth/register`: Register a new user.
  - Request body:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "password123"
    }
    ```
- `POST /api/auth/login`: Log in an existing user.
  - Request body:
    ```json
    {
      "email": "john.doe@example.com",
      "password": "password123"
    }
    ```

### Verification

- `GET /api/verify?token=<token>`: Verify a user's account using the token sent to their email.

## Configuration

The application uses environment variables for configuration. You can set them in your operating system or in a `.env` file.

- `DB_URL`: The URL of your MySQL database (e.g., `jdbc:mysql://localhost:3306/auth_db`)
- `DB_USERNAME`: The username for your database
- `DB_PASSWORD`: The password for your database
- `GMAIL_USERNAME`: Your Gmail address for sending verification emails
- `GMAIL_APP_PASSWORD`: Your Gmail App Password

## Getting Started

### Prerequisites

- JDK 25 or later
- Maven
- MySQL

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/Custom-Authentication-Mail-Service.git
   ```
2. Navigate to the `Authentication` directory:
   ```bash
   cd Custom-Authentication-Mail-Service/Authentication
   ```
3. Set the environment variables mentioned in the **Configuration** section.
4. Build the project:
   ```bash
   ./gradlew build
   ```
5. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The application will be running at `http://localhost:8080`.
