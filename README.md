# End-to-End Cloud-Based CI/CD Pipeline for Todo/Task Manager Application

## Project Overview

This project demonstrates the design and implementation of a complete Continuous Integration and Continuous Deployment (CI/CD) pipeline for a Java web-based Todo/Task Manager application using modern DevOps tools and practices. The pipeline automates building, testing, containerization, and deployment to a cloud-hosted Kubernetes environment.

## Architecture

The solution integrates the following components:
- **Git** - Source Code Management
- **Maven** - Build automation and dependency management for Java application
- **Docker** - Application containerization
- **Kubernetes** - Container orchestration and deployment
- **Jenkins/GitHub Actions** - CI/CD automation platform
- **Ansible** - Configuration management and deployment automation
- **Cloud Platform** (AWS/Azure/GCP) - Hosting infrastructure

## Application

A simple Spring Boot REST API Todo/Task Manager application that uses an in-memory ArrayList for data storage (no database required). 

**API Endpoints:**
- `GET /api/todos/` - Welcome message
- `GET /api/todos/health` - Health check endpoint
- `GET /api/todos` - Get all tasks
- `GET /api/todos/{id}` - Get task by ID
- `POST /api/todos` - Create a new task
- `PUT /api/todos/{id}` - Update a task
- `PATCH /api/todos/{id}/complete` - Mark task as completed
- `DELETE /api/todos/{id}` - Delete a task

## Project Structure

```
.
├── src/                    # Java source code
│   ├── main/
│   └── test/
├── Dockerfile             # Docker image definition
├── pom.xml               # Maven configuration
├── Jenkinsfile           # Jenkins pipeline configuration
├── .github/workflows/    # GitHub Actions workflow
├── k8s/                  # Kubernetes manifests
│   ├── deployment.yaml
│   └── service.yaml
├── ansible/              # Ansible playbooks
│   ├── inventory.ini
│   └── deploy-app.yml
└── README.md
```

## Prerequisites

- Java 17 JDK
- Maven 3.9+
- Docker
- Kubernetes cluster (EKS/AKS/GKE or local)
- kubectl configured
- CI/CD tool (Jenkins or GitHub Actions)
- Container registry account (Docker Hub)
- Ansible (for configuration management)

## Local Development

### Build and Run
```bash
mvn clean package
java -jar target/demo-app-1.0.0.jar
```

### Run with Docker
```bash
docker build -t demo-app:latest .
docker run -p 8080:8080 demo-app:latest
```

Application will be available at `http://localhost:8080/api/todos`

### Run Tests
```bash
mvn test
```

## CI/CD Pipeline Stages

1. **Checkout** - Clone source code from Git repository
2. **Compile** - Compile Java source code using Maven
3. **Test** - Run unit tests and generate reports
4. **Package** - Create JAR/WAR artifact
5. **Build Docker Image** - Build container image from Dockerfile
6. **Push to Registry** - Push image to Docker Hub/container registry
7. **Deploy to Kubernetes** - Apply K8s manifests and update deployment
8. **Verify** - Check deployment status and application availability

## Kubernetes Deployment

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl get pods
kubectl get services
```

## Ansible Deployment

```bash
ansible-playbook -i ansible/inventory.ini ansible/deploy-app.yml
```

## Environment Variables/Secrets Required

### GitHub Actions Secrets
- `DOCKER_USERNAME` - Docker Hub username
- `DOCKER_PASSWORD` - Docker Hub password/token
- `AWS_ACCESS_KEY_ID` - AWS credentials (for EKS)
- `AWS_SECRET_ACCESS_KEY` - AWS credentials
- `AWS_REGION` - AWS region
- `EKS_CLUSTER_NAME` - EKS cluster name

### Jenkins Credentials
- `dockerhub-credentials` - Docker Hub credentials
- `kubeconfig-credentials` - Kubernetes config file

## Security Considerations

- Use secrets management for credentials (Jenkins credentials store, GitHub Secrets)
- Implement least privilege access for service accounts
- Use multi-stage Docker builds to reduce image size
- Scan container images for vulnerabilities
- Use RBAC in Kubernetes
- Enable TLS/SSL for production endpoints

## Challenges and Solutions

| Challenge | Solution |
|-----------|----------|
| Managing credentials securely | Use CI/CD platform's secret management |
| Ensuring zero-downtime deployments | Use Kubernetes rolling updates |
| Image versioning | Tag images with build number and commit SHA |
| Environment consistency | Use Docker for containerization |

## Testing the Pipeline

1. Make a code change in the application
2. Commit and push to Git repository
3. Pipeline triggers automatically
4. Monitor pipeline execution through Jenkins/GitHub Actions UI
5. Verify new version is deployed to Kubernetes
6. Access application via LoadBalancer endpoint to confirm changes

## License

This project is created for educational purposes as part of CIA 2 assignment.
