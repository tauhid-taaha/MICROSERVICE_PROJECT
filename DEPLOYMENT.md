# Deployment Guide

## Local Development

### Prerequisites
- Java 21+
- Maven 3.6+
- MongoDB Atlas account
- Git

### Environment Setup
1. Clone repository
2. Install dependencies
3. Configure MongoDB connection
4. Start services in order

## Docker Deployment

### Using Docker Compose
```bash
# Start all services with MongoDB
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Individual Service Docker
```bash
# Build service
docker build -t service-name ./ServiceName

# Run service
docker run -p 8080:8080 service-name
```

## Production Deployment

### Environment Variables
```bash
# MongoDB
SPRING_DATA_MONGODB_URI=mongodb://user:pass@host:port/database

# Eureka
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka

# Service Ports
SERVER_PORT=8080
```

### Kubernetes Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
      - name: user-service
        image: user-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: SPRING_DATA_MONGODB_URI
          valueFrom:
            secretKeyRef:
              name: mongodb-secret
              key: uri
```

## Cloud Deployment

### AWS
- Use ECS or EKS for container orchestration
- RDS for MongoDB or DocumentDB
- Application Load Balancer for API Gateway
- CloudWatch for monitoring

### Azure
- Azure Container Instances or AKS
- CosmosDB for MongoDB
- Application Gateway
- Azure Monitor

### Google Cloud
- Google Kubernetes Engine (GKE)
- Cloud Firestore or MongoDB Atlas
- Cloud Load Balancing
- Cloud Monitoring

## Monitoring and Logging

### Health Checks
- Eureka dashboard: http://eureka-server:8761
- Service health endpoints: /actuator/health

### Logging
- Centralized logging with ELK stack
- Application logs in JSON format
- Error tracking and alerting

### Metrics
- Service response times
- Database connection pools
- Memory and CPU usage
- Request rates and error rates

## Security Considerations

### Network Security
- Use VPCs and security groups
- Enable TLS/SSL for all communications
- Implement API rate limiting

### Data Security
- Encrypt data at rest and in transit
- Use secure connection strings
- Implement proper access controls

### Application Security
- Regular dependency updates
- Security scanning in CI/CD
- Input validation and sanitization

## Backup and Recovery

### Database Backups
- Regular MongoDB backups
- Point-in-time recovery
- Cross-region replication

### Service Recovery
- Health check monitoring
- Automatic service restart
- Circuit breaker patterns

## Performance Optimization

### Caching
- Redis for session management
- Application-level caching
- Database query optimization

### Load Balancing
- Horizontal pod autoscaling
- Load balancer configuration
- Service mesh implementation

## Troubleshooting

### Common Issues
1. Service discovery failures
2. Database connection timeouts
3. Memory leaks and performance issues
4. Network connectivity problems

### Debug Commands
```bash
# Check service status
kubectl get pods
docker ps

# View logs
kubectl logs <pod-name>
docker logs <container-name>

# Check service health
curl http://service:port/actuator/health
```
