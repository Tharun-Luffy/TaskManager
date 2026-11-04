# TECHNICAL REPORT
## Design and Implementation of an End-to-End Cloud-Based CI/CD Pipeline

**Course:** DevOps  
**Assignment:** CIA 2  
**Date:** November 2026

---

## Table of Contents
1. [Problem Definition](#1-problem-definition)
2. [Architecture](#2-architecture)
3. [Tools Selected and Justification](#3-tools-selected-and-justification)
4. [CI/CD Pipeline Stages](#4-cicd-pipeline-stages)
5. [Cloud Resources and Deployment Approach](#5-cloud-resources-and-deployment-approach)
6. [Implementation Details](#6-implementation-details)
7. [Testing and Results](#7-testing-and-results)
8. [Challenges Encountered and Solutions](#8-challenges-encountered-and-solutions)
9. [Security Considerations and Limitations](#9-security-considerations-and-limitations)
10. [Conclusion](#10-conclusion)
11. [References](#11-references)

---

## 1. Problem Definition

The software development team faces challenges with manual deployment processes including building, testing, packaging, creating Docker images, and deploying applications. These manual processes are time-consuming, error-prone, and difficult to reproduce. 

The objective is to design and implement a complete Continuous Integration and Continuous Deployment (CI/CD) pipeline that automates the entire software delivery process from code commit to production deployment in a cloud-hosted Kubernetes environment.

---

## 2. Architecture

The end-to-end CI/CD pipeline follows this workflow:

```
Developer → Git Repository → CI/CD Platform (Jenkins/GitHub Actions) → 
Maven Build & Test → Docker Build → Container Registry (Docker Hub) → 
Kubernetes Cluster (Cloud) → LoadBalancer Service → End Users
```

**Architecture Components:**
- Source Control: Git
- Build Tool: Apache Maven
- CI/CD Orchestration: Jenkins/GitHub Actions
- Containerization: Docker
- Container Registry: Docker Hub
- Orchestration: Kubernetes
- Configuration Management: Ansible
- Cloud Infrastructure: AWS/Azure/GCP

---

## 3. Tools Selected and Justification

| Tool | Purpose | Justification |
|------|---------|---------------|
| **Git** | Version Control | Distributed VCS, enables collaboration and triggers automation |
| **Java Spring Boot** | Application Framework | Lightweight, production-ready framework for web applications |
| **Maven** | Build & Dependency Management | Standard for Java projects, simplifies build lifecycle |
| **JUnit** | Unit Testing | Industry standard for Java unit testing |
| **Docker** | Containerization | Ensures consistent environments across dev/test/prod |
| **Docker Hub** | Container Registry | Public/private repository for storing Docker images |
| **Kubernetes** | Container Orchestration | Auto-scaling, self-healing, rolling updates, load balancing |
| **Jenkins/GitHub Actions** | CI/CD Automation | Automates pipeline stages, integrates with all tools |
| **Ansible** | Configuration Management | Automates infrastructure setup and deployment tasks |
| **Cloud Provider (AWS)** | Infrastructure Hosting | Scalable, managed services (EKS), reliable |

---

## 4. CI/CD Pipeline Stages

| Stage | Description | Purpose |
|-------|-------------|---------|
| **1. Checkout** | Clone repository from Git | Fetch latest source code |
| **2. Compile** | `mvn clean compile` | Compile Java source files |
| **3. Test** | `mvn test` | Execute unit tests, validate functionality |
| **4. Package** | `mvn package` | Create executable JAR artifact |
| **5. Build Docker Image** | `docker build` | Containerize the application |
| **6. Push Image** | `docker push` | Publish to container registry with version tags |
| **7. Deploy** | `kubectl apply` | Deploy/update application on Kubernetes |
| **8. Verify** | Health checks, status validation | Ensure successful deployment and availability |

---

## 5. Cloud Resources and Deployment Approach

**Cloud Platform:** Amazon Web Services (AWS) - Elastic Kubernetes Service (EKS)

**Resources Provisioned:**
- **EKS Cluster** - Managed Kubernetes control plane
- **EC2 Worker Nodes** - Compute instances for running pods
- **VPC, Subnets, Security Groups** - Network infrastructure
- **IAM Roles** - Access management
- **Elastic Load Balancer** - External traffic routing
- **Docker Hub** - Container image storage

**Deployment Strategy:** Rolling Update
- Zero-downtime deployments
- Gradual replacement of pods
- Automatic rollback on failure
- Health checks via readiness and liveness probes

---

## 6. Implementation Details

### 6.1 Application Code
Simple REST API with Spring Boot exposing health and home endpoints for demonstration.

### 6.2 Dockerfile
Multi-stage build for optimized image size:
- Build stage: Maven with JDK 17
- Runtime stage: JRE 17 only

### 6.3 Kubernetes Manifests
- **Deployment:** 3 replicas for high availability, resource limits, health probes
- **Service:** LoadBalancer type for external access

### 6.4 Pipeline Configuration
Both Jenkinsfile and GitHub Actions workflow provided with complete automation.

### 6.5 Ansible Automation
Playbooks for Docker installation on worker nodes and application deployment to Kubernetes.

---

## 7. Testing and Results

**Unit Testing:**
- All unit tests pass successfully using JUnit and Spring MockMvc
- Test coverage for controller endpoints

**Integration Testing:**
- Docker image builds successfully
- Container runs without errors
- Kubernetes deployment creates pods in Running state
- Service accessible via LoadBalancer endpoint
- Health check endpoint responds correctly

**Pipeline Testing:**
- Automatic trigger on Git push works as expected
- All pipeline stages execute successfully
- Image versioning with build number/commit SHA
- Rolling update deploys new version seamlessly

---

## 8. Challenges Encountered and Solutions

| Challenge | Solution |
|-----------|----------|
| **Credentials Management** | Implemented secure credential storage using Jenkins Credentials and GitHub Secrets |
| **Kubernetes Access** | Configured kubeconfig securely in CI/CD environment |
| **Image Tagging** | Used dynamic tagging with BUILD_NUMBER and Git commit SHA for traceability |
| **Service Discovery** | Used Kubernetes Service with LoadBalancer for external access |
| **Health Monitoring** | Implemented readiness and liveness probes in Kubernetes |
| **Multi-environment Setup** | Parameterized configurations for different environments |

---

## 9. Security Considerations and Limitations

**Security Measures:**
- Secrets stored securely in CI/CD platforms (never hardcoded)
- Multi-stage Docker builds minimize attack surface
- Resource limits prevent resource exhaustion
- Kubernetes RBAC for access control
- Use of official base images
- .dockerignore to exclude sensitive files

**Limitations:**
- Requires cloud infrastructure costs
- LoadBalancer has associated costs
- Manual setup of initial cluster required
- Secrets need to be configured per environment
- No advanced security scanning integrated (can be added)

---

## 10. Conclusion

This project successfully demonstrates the implementation of an end-to-end CI/CD pipeline for a Java web application. The automation eliminates manual errors, reduces deployment time, and ensures consistency across environments. The integration of Git, Maven, Docker, Kubernetes, CI/CD tools, and Ansible provides a robust, scalable, and reproducible deployment process. The solution meets all the deliverables specified in the assignment and showcases industry-standard DevOps practices.

---

## 11. References

1. Jenkins Documentation - https://www.jenkins.io/doc/
2. GitHub Actions Documentation - https://docs.github.com/en/actions
3. Docker Documentation - https://docs.docker.com/
4. Kubernetes Documentation - https://kubernetes.io/docs/
5. Apache Maven Documentation - https://maven.apache.org/guides/
6. Spring Boot Documentation - https://spring.io/projects/spring-boot
7. Ansible Documentation - https://docs.ansible.com/
8. AWS EKS Documentation - https://docs.aws.amazon.com/eks/
