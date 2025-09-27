#!/bin/bash

echo "Starting Microservices Job Portal System..."
echo

echo "Starting Service Discovery..."
gnome-terminal --title="Service Discovery" -- bash -c "cd Service_Discovery && ./mvnw spring-boot:run; exec bash"
sleep 10

echo "Starting API Gateway..."
gnome-terminal --title="API Gateway" -- bash -c "cd Gateway && ./mvnw spring-boot:run; exec bash"
sleep 5

echo "Starting User Service..."
gnome-terminal --title="User Service" -- bash -c "cd UserService && ./mvnw spring-boot:run; exec bash"
sleep 5

echo "Starting Application Service..."
gnome-terminal --title="Application Service" -- bash -c "cd ApplicationService && ./mvnw spring-boot:run; exec bash"
sleep 5

echo "Starting Event Service..."
gnome-terminal --title="Event Service" -- bash -c "cd EventService && ./mvnw spring-boot:run; exec bash"

echo
echo "All services are starting..."
echo
echo "Service URLs:"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- User Service: http://localhost:8081"
echo "- Application Service: http://localhost:8083"
echo "- Event Service: http://localhost:8082"
echo
echo "Press any key to exit..."
read -n 1
