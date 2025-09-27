@echo off
echo Starting Microservices Job Portal System...
echo.

echo Starting Service Discovery...
start "Service Discovery" cmd /k "cd Service_Discovery && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo Starting API Gateway...
start "API Gateway" cmd /k "cd Gateway && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Starting User Service...
start "User Service" cmd /k "cd UserService && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Starting Application Service...
start "Application Service" cmd /k "cd ApplicationService && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Starting Event Service...
start "Event Service" cmd /k "cd EventService && mvn spring-boot:run"

echo.
echo All services are starting...
echo.
echo Service URLs:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - User Service: http://localhost:8081
echo - Application Service: http://localhost:8083
echo - Event Service: http://localhost:8082
echo.
echo Press any key to exit...
pause > nul
