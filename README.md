# 🎉 **EventSphere**

This project demonstrates a **microservices-based event management system**, designed to showcase service communication patterns in a distributed architecture. The system is composed of several independently deployable services, each with its own database, working together to provide comprehensive event management functionality.

---

## **Technology Stack:**

- **Backend Services:**
  - **Spring Boot (Java)** for building RESTful APIs and services.
  
- **Service Discovery:**
  - **Netflix Eureka** for service registration and discovery.

- **API Gateway:**
  - **Spring Cloud Gateway** handles routing and load balancing for the microservices.

- **User Management:**
  - **Node.js** for managing user-related features such as authentication and authorization.

- **Frontend:**
  - **Angular** for building a dynamic user interface for event management and booking.

- **Containerization:**
  - **Docker**: Used to containerize all services for consistent deployment and scalability.

---

## 👥 Module Contributors

| Module                   | Contributor        | Database     |
|--------------------------|--------------------|--------------|
| Event Management         | Trabelsi Nour      | MongoDB      |
| Partnership Management   | Soufien Ben Salah  | MySQL        |
| Payment Management       | Yassine Riahi      | H2           |
| Reclamation Management   | Jendoubi Hafedh    | MySQL        |
| Resource Management      | Wiem Ben Salah     | PostgreSQL   |

---

## 📁 Project Structure

```
EventSphere/
├── backend/
│   ├── Eureka/
│   ├── Gateway/
│   ├── event-management/
│   ├── partnership-management/
│   ├── payment-management/
│   ├── reclamation-management/
│   ├── resource-management/
│   └── docker-compose.yml
├── frontend/
│   ├── src/
│   └── ...
```

---

## **Clone and Run the Project**

### **1. Clone the Repository**

To get started, clone the project repository to your local machine:

```bash
git clone https://github.com/your-repo/event-sphere.git
cd event-sphere
```

### **2. Build the Project**
Navigate to each microservice directory and build the project using Maven:

```bash
mvn clean package
```
### **3. Run the Project with Docker Compose**
```bash
docker-compose up --build
```

---

## 📝 License

MIT License
