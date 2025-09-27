# Quick Setup Guide

## Prerequisites
- Java 21 or higher
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB)
- Git

## Quick Start

### 1. Clone and Setup
```bash
git clone <your-repo-url>
cd MICROSERVICE_PROJECT
```

### 2. Install Maven (if needed)
```bash
# Windows with Chocolatey
choco install maven

# Or download from: https://maven.apache.org/download.cgi
```

### 3. Start Services
Use the provided scripts:

**Windows:**
```bash
start-services.bat
```

**Linux/Mac:**
```bash
./start-services.sh
```

**Manual (5 terminals):**
```bash
# Terminal 1
cd Service_Discovery && ./mvnw spring-boot:run

# Terminal 2  
cd Gateway && ./mvnw spring-boot:run

# Terminal 3
cd UserService && ./mvnw spring-boot:run

# Terminal 4
cd ApplicationService && ./mvnw spring-boot:run

# Terminal 5
cd EventService && ./mvnw spring-boot:run
```

### 4. Verify
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- All services should appear in Eureka

## Troubleshooting

### Common Issues
1. **Port already in use**: Kill processes using ports 8080-8083, 8761
2. **MongoDB connection**: Check your MongoDB Atlas connection string
3. **Service not starting**: Check Java version (needs Java 21+)

### Logs
Check console output for any error messages. Services will show startup logs.

## Next Steps
- Create users via API
- Create events
- Submit applications
- Check Eureka dashboard for service health
