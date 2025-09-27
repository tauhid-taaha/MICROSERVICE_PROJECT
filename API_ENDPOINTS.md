# API Endpoints Documentation

This document provides detailed information about all available API endpoints in the Microservices Job Portal System.

## Base URLs

- **API Gateway**: http://localhost:8080
- **Service Discovery**: http://localhost:8761
- **User Service**: http://localhost:8081
- **Application Service**: http://localhost:8083
- **Event Service**: http://localhost:8082

## User Service Endpoints

### Create User
```http
POST /api/users
Content-Type: application/json

{
  "username": "string",
  "email": "string",
  "password": "string",
  "roles": ["PARTICIPANT" | "EVENT_MANAGER"],
  "phone": "string",
  "companyName": "string" // Required for EVENT_MANAGER
}
```

### Get All Users
```http
GET /api/users
```

### Get User by ID
```http
GET /api/users/{id}
```

### Get User by Username
```http
GET /api/users/username/{username}
```

### Update User
```http
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "string",
  "email": "string",
  "password": "string",
  "roles": ["PARTICIPANT" | "EVENT_MANAGER"],
  "phone": "string",
  "companyName": "string"
}
```

### Delete User
```http
DELETE /api/users/{id}
```

### Assign Role (Admin Only)
```http
POST /api/users/{id}/roles
Content-Type: application/json

"ROLE_NAME"
```

### Remove Role (Admin Only)
```http
DELETE /api/users/{id}/roles/{role}
```

### Get User Roles
```http
GET /api/users/{id}/roles
```

## Application Service Endpoints

### Submit Job Application
```http
POST /api/applications
Content-Type: application/json

{
  "jobId": "string",
  "jobSeekerId": "string",
  "name": "string",
  "phone": "string",
  "email": "string",
  "cvFileUrl": "string",
  "skills": ["string"],
  "experience": "string",
  "degree": "string"
}
```

### Get All Applications
```http
GET /api/applications
```

### Get Application by ID
```http
GET /api/applications/{id}
```

### Get Applications by Job
```http
GET /api/applications/job/{jobId}
```

### Get Applications by Job Seeker
```http
GET /api/applications/jobseeker/{jobSeekerId}
```

### Get Applications by Job and Status
```http
GET /api/applications/job/{jobId}/status/{status}
```

### Get Applications by Job Seeker and Status
```http
GET /api/applications/jobseeker/{jobSeekerId}/status/{status}
```

### Update Application Status
```http
PATCH /api/applications/{id}/status/{status}
```

**Status Values**: `PENDING`, `ACCEPTED`, `REJECTED`

### Delete Application
```http
DELETE /api/applications/{id}
```

### Get Application Count for Job
```http
GET /api/applications/job/{jobId}/count
```

### Get Pending Applications Count for Job
```http
GET /api/applications/job/{jobId}/pending/count
```

### Check if User Can Apply
```http
GET /api/applications/can-apply?jobSeekerId={jobSeekerId}&jobId={jobId}
```

## Event Service Endpoints

### Create Event
```http
POST /api/events
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "location": "string",
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-01T18:00:00",
  "capacity": 100,
  "organizerId": "string"
}
```

### Get All Events
```http
GET /api/events
```

### Get Event by ID
```http
GET /api/events/{id}
```

### Get Events by Organizer
```http
GET /api/events/organizer/{organizerId}
```

### Search Events by Title
```http
GET /api/events/search/title?title={title}
```

### Update Event
```http
PUT /api/events/{id}
Content-Type: application/json

{
  "title": "string",
  "description": "string",
  "location": "string",
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-01T18:00:00",
  "capacity": 100,
  "organizerId": "string"
}
```

### Delete Event
```http
DELETE /api/events/{id}
```

## Response Formats

### Success Response
```json
{
  "id": "string",
  "field1": "value1",
  "field2": "value2"
}
```

### Error Response
```json
{
  "error": "Error Type",
  "message": "Error description",
  "timestamp": 1234567890
}
```

### Count Response
```json
{
  "count": 42
}
```

### Can Apply Response
```json
{
  "canApply": true
}
```

## HTTP Status Codes

- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Request successful, no content returned
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists
- `500 Internal Server Error` - Server error

## Authentication

Currently, the system uses basic authentication. Include credentials in the request headers:

```http
Authorization: Basic base64(username:password)
```

## Rate Limiting

No rate limiting is currently implemented, but it's recommended for production use.

## CORS

CORS is enabled for all origins. For production, configure specific allowed origins.

## Example Usage

### Complete Workflow Example

1. **Create a User (Event Manager)**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "eventmanager1",
    "email": "manager@company.com",
    "password": "password123",
    "roles": ["EVENT_MANAGER"],
    "phone": "1234567890",
    "companyName": "Tech Corp"
  }'
```

2. **Create an Event**
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Java Developer Meetup",
    "description": "Learn about Spring Boot microservices",
    "location": "Tech Hub, Room 101",
    "startDate": "2024-02-01T18:00:00",
    "endDate": "2024-02-01T20:00:00",
    "capacity": 50,
    "organizerId": "user_id_here"
  }'
```

3. **Create a Job Seeker**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "jobseeker1",
    "email": "seeker@example.com",
    "password": "password123",
    "roles": ["PARTICIPANT"],
    "phone": "0987654321"
  }'
```

4. **Submit Job Application**
```bash
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "jobId": "job_id_here",
    "jobSeekerId": "jobseeker_id_here",
    "name": "John Doe",
    "phone": "0987654321",
    "email": "seeker@example.com",
    "skills": ["Java", "Spring Boot", "Microservices"],
    "experience": "3 years",
    "degree": "Computer Science"
  }'
```

5. **Update Application Status**
```bash
curl -X PATCH http://localhost:8080/api/applications/{application_id}/status/ACCEPTED
```

## Notes

- All timestamps are in ISO 8601 format
- All IDs are strings (MongoDB ObjectIds)
- Phone numbers should be 10-15 digits
- Email addresses must be valid format
- Status values are case-insensitive but stored in uppercase
- All services are accessible through the API Gateway at port 8080
