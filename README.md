# Bus Reservation System

A modern Bus Reservation System built with Spring Boot 3.x, MySQL, and Thymeleaf.

## Tech Stack

- **Language**: Java 17 (OpenJDK)
- **Framework**: Spring Boot 3.2.1
  - Spring Web
  - Spring Data JPA
  - Spring DevTools
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf + Bootstrap 5
- **Boilerplate**: Lombok
- **Build Tool**: Maven

## Database Schema

### Bus Entity
- `id` (Long, Primary Key)
- `busNumber` (String)
- `source` (String)
- `destination` (String)
- `price` (Double)
- `departureTime` (LocalDateTime)
- `totalSeats` (Integer)

### Reservation Entity
- `id` (Long, Primary Key)
- `passengerName` (String)
- `seatNumber` (Integer)
- `reservationDate` (LocalDateTime)
- `bus` (Many-to-One relationship with Bus)

## Prerequisites

1. **Java 17**: Ensure JDK 17 is installed
2. **Maven**: Apache Maven 3.6+
3. **MySQL 8.0**: Running locally on port 3306

## Database Setup

1. Install and start MySQL server
2. The application will automatically create the `bus_reservation` database on first run
3. Default configuration uses:
   - Username: `root`
   - Password: `` (empty)
   - Port: `3306`

> **Note**: Update credentials in `src/main/resources/application.properties` if needed.

## Running the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Project Structure

```
Bus_Reservation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/busreservation/
│   │   │       ├── BusReservationApplication.java
│   │   │       ├── entity/          # Domain entities
│   │   │       ├── repository/      # Data access layer
│   │   │       ├── service/         # Business logic
│   │   │       └── controller/      # Web controllers
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/           # Thymeleaf HTML templates
│   │       └── static/              # CSS, JS, images
│   └── test/
├── pom.xml
└── README.md
```

## Development Phases
**Phase 1**: Project configuration (pom.xml & application.properties)
**Phase 2**: Domain layer (Entities & Repositories)
**Phase 3**: Service layer (Business logic)
**Phase 4**: Controller & View layer (Thymeleaf templates)

## Features (Planned)

- View available buses
- Search buses by source and destination
- Book bus tickets
- View reservations
- Admin panel for bus management

---
