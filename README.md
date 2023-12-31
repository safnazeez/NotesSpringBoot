# Notes Spring Boot API

This is a Spring Boot project serving as a backend API for managing text notes and images within folders. Users can create folders, each of which can be designated as either public or private. The project employs JWT (JSON Web Token) for authentication and authorization.

The key functionalities are:

1. **User Registration:**
   - Use the "register" API endpoint to create a new user account.
   - Provide necessary information such as name, mobile, and password.

2. **User Login:**
   - Utilize the "login" API endpoint for user authentication.
   - Upon successful login, the server generates a JWT token.
   - Include the token in the Authorization header using Bearer token syntax for subsequent authorized requests.

3. **Note and Image Management:**
   - Authenticated users can add text notes and images.
   - Each note or image is associated with a specific folder.
   - Users can organize their content within both public and private folders.

4. **JWT Token Usage:**
   - Include the JWT token in the Authorization header for secure and authorized access.
   - The header should have the following format:
     ```
     Authorization: Bearer <your-jwt-token>
     ```
   - Replace `<your-jwt-token>` with the actual token obtained during the login process.

5. **API Documentation:**
   - Refer to the [Postman documentation](https://documenter.postman.com/view/4630964/S1LsXVJy) for detailed information on available API endpoints, request formats, and examples.

This Spring Boot API backend provides a secure and organized way for users to manage their textual notes and images within customizable folders. The use of JWT tokens ensures that only authenticated and authorized users can interact with the API endpoints. The API documentation in the provided Postman URL offers comprehensive guidance on utilizing the available functionalities.

## Prerequisites

Make sure you have the following installed:

- [Java Development Kit (JDK)](https://adoptopenjdk.net/)
- [Apache Maven](https://maven.apache.org/)

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/safnazeez/NotesSpringBoot.git
    ```

2. Navigate to the project directory:

    ```bash
    cd NotesSpringBoot
    ```

## Running the Application

Build and run the project using Maven:

```bash
mvn spring-boot:run
```

## API Documentation
The API documentation is available in Postman. Please click the link below

```bash
   https://documenter.getpostman.com/view/3047950/2s9YsDkFMy
```
