# MTG Deckbuilder Backend

A Spring Boot-based backend for a Magic: The Gathering (MTG) Deckbuilder application. This service handles deck persistence, card management, and user authentication via Auth0.

## Features

- **Authentication**: Secured with Auth0 using JWT (OAuth2 Resource Server).
- **Deck Management**: Create, Read, Update, and Delete (CRUD) operations for MTG decks.
- **Data Persistence**: Uses PostgreSQL for reliable storage.
- **User Isolation**: Each user can only access and modify their own decks.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Security (OAuth2 Resource Server)**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL
- Auth0 Account (for authentication)

### Configuration

Update `src/main/resources/application.properties` with your local settings:

1.  **Database**:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

2.  **Auth0**:
    ```properties
    spring.security.oauth2.resourceserver.jwt.issuer-uri=https://your-domain.auth0.com/
    spring.security.oauth2.resourceserver.jwt.audience=https://your-api-identifier
    ```

### Running the Application

Navigate to the project directory and run:

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

## API Endpoints

All endpoints (except preflight requests) require a valid JWT from Auth0 in the `Authorization: Bearer <token>` header.

- `GET /api/decks` - List all decks for the authenticated user.
- `GET /api/decks/{id}` - Get details for a specific deck.
- `POST /api/decks` - Create a new deck.
- `PUT /api/decks/{id}` - Update deck metadata (name, format, description).
- `DELETE /api/decks/{id}` - Delete a deck.
- `PUT /api/decks/{id}/cards` - Add or update a card in a deck.
- `DELETE /api/decks/{id}/cards/{oracleId}` - Remove a card from a deck.

## Testing

Run the automated tests using:

```bash
mvn test
```
