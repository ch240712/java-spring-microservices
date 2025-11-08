# java-spring-microservices
Sample source code as a personal project to reinforce and refresh software engineering principles and technical skills. This app lists the items in a customer’s shopping cart.

Inspired by and adapted from Chris Blakely's comprehensive and excellent YouTube course on software engineering: Build & Deploy a Production-Ready Patient Management System with Microservices: Java Spring Boot AWS.

HTTP requests and responses:

1) Log in to generate Jason Web Token.

Request:

POST http://localhost:4002/auth/login

With body:

{
"email": "testuser@test.com",
"password": "password123"
}

Response:

{
    "token": "<token>"
}

2) Use JWT token in HTTP Authorization Bearer header to authenticate client request to get shopping cart items for given customer.

Request:

GET http://localhost:4002/api/cart/223e4567-e89b-12d3-a456-426614174008

With headers:

Authorization Bearer <token>

Response:

{
    "customerId": "223e4567-e89b-12d3-a456-426614174008",
    "items": [
        {
            "productId": "123e4567-e89b-12d3-a456-426614174000",
            "name": "Sony PS5",
            "description": "Sony PS5 game console",
            "unitPrice": 399.00,
            "count": 1,
            "price": 399.00
        },
        {
            "productId": "123e4567-e89b-12d3-a456-426614174003",
            "name": "Blue Horizon Tent",
            "description": "Blue Horizon one-man tent",
            "unitPrice": 199.99,
            "count": 3,
            "price": 599.97
        }
    ],
    "error": null
}

Tech used:

- IntelliJ IDEA with Maven
- Docker Desktop
- Apidog
- pgAdmin 4
- Spring Boot microservices
- Spring Cloud API gateway
- Spring Security and Jason Web Token
- Spring Data JPA
- PostgreSQL database (from Docker Hub)
- springdoc-openapi for API documentation (supports Swagger-ui)
- Eureka Service Discovery
- Resilience4j circuit breaker fault tolerance
- JUnit and REST Assured integration tests

Run application in following order:

Docker agent (e.g., Docker Desktop)
shop-db and auth-service-db (Docker settings below)
EurekaService
AuthService
ApiGateway
CartService
ProductService

Settings:

PostgreSQL Docker settings for shop database:

Name: shop-db
Server: Docker
Image ID or name: postgres: 18.0
Container name: shop-db
Bind ports: 5000:5432
Bind mounts:
- Host path: C:/<local_folder>/db_volumes/shop-db
- Container path: /var/lib/postgresql/18/docker
  Environment Variables:
  POSTGRES_DB=db
  POSTGRES_PASSWORD=password
  POSTGRES_USER=admin_user
  Run options: --network internal

PostgreSQL Docker settings for auth database:

Same setting as above except for:

Name: auth-service-db
Container name: auth-service-db
Bind ports: 5001:5432
Bind mounts:
- Host path: C:/<local_folder>/db_volumes/auth-service-db
- Container path: /var/lib/postgresql/18/docker

Auth Service Java module:

Environment Variables:
JWT_SECRET=a-string-secret-at-least-256-bits-long