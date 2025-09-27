# Microservices Job Portal System

A comprehensive microservices-based job portal system built with Spring Boot, featuring service discovery, API gateway, and multiple specialized services for user management, job applications, and event management.

## 🏗️ Architecture Overview

This project implements a microservices architecture with the following components:

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

## 🚀 Services

### 1. Service Discovery (Eureka Server)
- **Port**: 8761
- **Purpose**: Centralized service registry and discovery
- **Technology**: Spring Cloud Netflix Eureka
- **URL**: http://localhost:8761

### 2. API Gateway
- **Port**: 8080
- **Purpose**: Single entry point for all client requests
- **Technology**: Spring Cloud Gateway
- **Features**: Request routing, load balancing, service discovery integration

### 3. User Service
- **Port**: 8081
- **Purpose**: User management and authentication
- **Database**: MongoDB (hirenow_users)
- **Features**:
  - User registration and authentication
  - Role-based access control (PARTICIPANT, EVENT_MANAGER)
  - User profile management
  - Company information for event managers

### 4. Application Service
- **Port**: 8083
- **Purpose**: Job application management
- **Database**: MongoDB (hirenow_applications)
- **Features**:
  - Job application submission
  - Application status tracking (PENDING, ACCEPTED, REJECTED)
  - Application filtering and search
  - Duplicate application prevention
  - Application statistics and analytics

### 5. Event Service
- **Port**: 8082
- **Purpose**: Event management and organization
- **Database**: MongoDB (hirenow_events)
- **Features**:
  - Event creation and management
  - Event search and filtering
  - Organizer-specific event management
  - Event capacity management

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.x
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: MongoDB Atlas (Cloud)
- **Build Tool**: Maven
- **Java Version**: 8+
- **Validation**: Jakarta Validation
- **Security**: Spring Security (Basic)

## 📋 Prerequisites

Before running the application, ensure you have:

1. **Java 8 or higher** installed
2. **Maven** installed
3. **Git** installed
4. **MongoDB Atlas** account (or local MongoDB instance)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd MICROSERVICE_PROJECT
```

### 2. Install Maven (if not already installed)

#### Using Chocolatey (Windows):
```bash
# Install Chocolatey first
powershell -Command "Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))"

# Install Maven
choco install maven -y
```

#### Using Scoop (Windows):
```bash
# Install Scoop
powershell -Command "Set-ExecutionPolicy RemoteSigned -Scope CurrentUser; irm get.scoop.sh | iex"

# Install Maven
scoop install maven
```

### 3. Start the Services

**Important**: Start services in the following order:

#### Terminal 1 - Service Discovery
```bash
cd Service_Discovery
./mvnw spring-boot:run
```

#### Terminal 2 - API Gateway
```bash
cd Gateway
./mvnw spring-boot:run
```

#### Terminal 3 - User Service
```bash
cd UserService
./mvnw spring-boot:run
```

#### Terminal 4 - Application Service
```bash
cd ApplicationService
./mvnw spring-boot:run
```

#### Terminal 5 - Event Service
```bash
cd EventService
./mvnw spring-boot:run
```

## 🌐 API Endpoints

### Service Discovery
- **Eureka Dashboard**: http://localhost:8761

### API Gateway (All requests go through this)
- **Base URL**: http://localhost:8080

### User Service
- `POST /api/users` - Create user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `POST /api/users/{id}/roles` - Assign role
- `DELETE /api/users/{id}/roles/{role}` - Remove role

### Application Service
- `POST /api/applications` - Submit job application
- `GET /api/applications` - Get all applications
- `GET /api/applications/{id}` - Get application by ID
- `GET /api/applications/job/{jobId}` - Get applications by job
- `GET /api/applications/jobseeker/{jobSeekerId}` - Get applications by job seeker
- `PATCH /api/applications/{id}/status/{status}` - Update application status
- `DELETE /api/applications/{id}` - Delete application
- `GET /api/applications/job/{jobId}/count` - Get application count for job
- `GET /api/applications/can-apply` - Check if user can apply

### Event Service
- `POST /api/events` - Create event
- `GET /api/events` - Get all events
- `GET /api/events/{id}` - Get event by ID
- `GET /api/events/organizer/{organizerId}` - Get events by organizer
- `GET /api/events/search/title?title={title}` - Search events by title
- `PUT /api/events/{id}` - Update event
- `DELETE /api/events/{id}` - Delete event

## 📊 Database Schema

### Users Collection
```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "password": "string",
  "roles": ["PARTICIPANT", "EVENT_MANAGER"],
  "phone": "string",
  "companyName": "string"
}
```

### Applications Collection
```json
{
  "id": "string",
  "jobId": "string",
  "jobSeekerId": "string",
  "name": "string",
  "phone": "string",
  "email": "string",
  "cvFileUrl": "string",
  "skills": ["string"],
  "experience": "string",
  "degree": "string",
  "status": "PENDING|ACCEPTED|REJECTED",
  "applicationDate": "datetime"
}
```

### Events Collection
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  "location": "string",
  "startDate": "datetime",
  "endDate": "datetime",
  "capacity": "number",
  "organizerId": "string",
  "createdDate": "datetime"
}
```

## 🔧 Configuration

### MongoDB Configuration
The application uses MongoDB Atlas cloud database. Update the connection string in each service's `application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/database
```

### Service Discovery Configuration
All services are configured to register with Eureka server at `http://localhost:8761/eureka`.

## 🧪 Testing the Application

### 1. Check Service Registration
Visit http://localhost:8761 to see all registered services in the Eureka dashboard.

### 2. Test API Endpoints
Use tools like Postman or curl to test the API endpoints through the gateway:

```bash
# Test user creation
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "roles": ["PARTICIPANT"],
    "phone": "1234567890"
  }'

# Test application submission
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "jobId": "job123",
    "jobSeekerId": "user123",
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "skills": ["Java", "Spring Boot"],
    "experience": "2 years",
    "degree": "Computer Science"
  }'
```

## 🚨 Troubleshooting

### Common Issues

1. **Service Discovery Not Working**
   - Ensure Service Discovery is started first
   - Check if port 8761 is available
   - Verify Eureka configuration in each service

2. **Database Connection Issues**
   - Verify MongoDB Atlas connection string
   - Check network connectivity
   - Ensure database credentials are correct

3. **Service Communication Issues**
   - Check if all services are registered in Eureka
   - Verify service names in application.properties
   - Check if services are running on correct ports

4. **Maven Build Issues**
   - Ensure Java 8+ is installed
   - Check Maven installation
   - Run `mvn clean install` in each service directory

## 📝 Development Notes

- Each service is independently deployable
- Services communicate through REST APIs
- Service discovery enables dynamic service location
- API Gateway provides centralized request routing
- MongoDB collections are automatically created on first use

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions, please contact the development team or create an issue in the repository.

---

**Happy Coding! 🚀**
