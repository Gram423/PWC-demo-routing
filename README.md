# PWC Demo Routing Application

A Spring Boot application built with Kotlin that provides REST API functionality for finding routes between countries. The application uses graph algorithms to determine optimal paths between different country codes.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing the API](#testing-the-api)
- [Running Tests](#running-tests)

## Prerequisites

- **Java 17** or higher (Java 21 LTS recommended)
- **Maven 3.6+** (Maven wrapper is included in the project)
- **Terminal/Command Line** access

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Gram423/PWC-demo-routing.git
   cd PWC-demo-routing
   ```

2. **Verify Java version:**
   ```bash
   java -version
   ```
   Make sure you have Java 24 or higher installed.

3. **Build the application:**
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw clean compile
   
   # Or using installed Maven
   mvn clean compile
   ```


## Running the Application

### Option 1: Using Maven Wrapper (Recommended)

bash
# On macOS/Linux
./mvnw spring-boot:run
# On Windows
mvnw.cmd spring-boot:run


### Option 2: Using installed Maven
```bash
mvn spring-boot:run
```
The application will start on **[http://localhost:8080](http://localhost:8080)**

## API Endpoints
### Get Route Between Countries
- **URL:** `GET /routing/{origin}/{destination}`
- **Description:** Find the optimal route between two countries
- **Parameters:**
   - `origin` (string): Origin country code (e.g., "CZE")
   - `destination` (string): Destination country code (e.g., "ITA")

- **Response:** JSON object containing the route
- **Status Codes:**
   - `200 OK`: Route found successfully
   - `400 Bad Request`: No route available or invalid country codes
   - 
## Testing the API
### Using cURL
1. **Test a valid route:**

   curl -X GET "http://localhost:8080/routing/CZE/ITA" \
   -H "Content-Type: application/json"

Expected response:

{
"route": ["CZE", "AUT", "ITA"]
}

1. **Test invalid route (should return 400 Bad Request):**

   curl -X GET "http://localhost:8080/routing/USA/CHN" \
   -H "Content-Type: application/json" \
   -w "\nHTTP Status: %{http_code}\n"

## Troubleshooting
### Application won't start
- Check if port 8080 is already in use
  lsof -i :8080
- Kill any process using port 8080:
-   kill -9 <PID>

### Java version issues
- Make sure you're using Java 24:
    java -version
    javac -version



## Stop the Application
Press `Ctrl + C` in the terminal where the application is running.
## Additional Notes
- The application runs on port 8080 by default
- All API responses are in JSON format
- The application supports European country routing based on geographical borders
- Input validation ensures proper country code format and route availability

