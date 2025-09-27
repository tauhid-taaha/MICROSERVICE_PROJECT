# System Architecture

## Overview
This microservices system implements a job portal with event management capabilities using Spring Boot and Spring Cloud.

## Architecture Diagram

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │ Service Discovery│    │   UserService   │
│   (Port 8080)   │◄──►│   (Port 8761)   │◄──►│   (Port 8081)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ApplicationService│    │  EventService   │    │   MongoDB       │
│   (Port 8083)   │    │   (Port 8082)   │    │   (Cloud)       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Service Responsibilities

### Service Discovery (Eureka)
- **Port**: 8761
- **Role**: Centralized service registry
- **Technology**: Netflix Eureka Server
- **Purpose**: Enables service-to-service communication

### API Gateway
- **Port**: 8080
- **Role**: Single entry point for all client requests
- **Technology**: Spring Cloud Gateway
- **Features**: Request routing, load balancing, service discovery

### User Service
- **Port**: 8081
- **Role**: User management and authentication
- **Database**: MongoDB (hirenow_users)
- **Features**: User CRUD, role management, password encryption

### Application Service
- **Port**: 8083
- **Role**: Job application management
- **Database**: MongoDB (hirenow_applications)
- **Features**: Application submission, status tracking, validation

### Event Service
- **Port**: 8082
- **Role**: Event management and organization
- **Database**: MongoDB (hirenow_events)
- **Features**: Event CRUD, search, organizer management

## Data Flow

1. **Client Request** → API Gateway
2. **Gateway** → Routes to appropriate service
3. **Service** → Registers with Eureka
4. **Service** → Communicates with MongoDB
5. **Service** → Returns response via Gateway

## Technology Stack

- **Backend**: Spring Boot 3.4.9
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: MongoDB Atlas
- **Build Tool**: Maven
- **Java Version**: 21
- **Cloud**: Spring Cloud 2024.0.2

## Security

- Password encryption using BCrypt
- Role-based access control
- Input validation and sanitization
- Service-to-service communication security

## Scalability

- Microservices architecture enables independent scaling
- Service discovery allows dynamic service location
- Database separation prevents cross-service data conflicts
- Load balancing through API Gateway

## Monitoring

- Eureka dashboard for service health
- Application logs for debugging
- Service registration and discovery monitoring
