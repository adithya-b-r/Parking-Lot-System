# Parking Lot System (Low-Level Design)

A Java-based command-line application demonstrating the Low-Level Design (LLD) of a Parking Lot System. The project uses a layered architecture (Model-Repository-Service) and integrates with a MySQL database via JDBC.

## Architecture & Components

The codebase is organized into several packages:

*   **Models ([model](./model))**: Core entities representing the system domain.
    *   [Vehicle.java](./model/Vehicle.java): Represents vehicles with properties like `vehicleNumber` and `vehicleType` (`BIKE`, `CAR`, `TRUCK`).
    *   [ParkingSlot.java](./model/ParkingSlot.java): Represents individual parking slots with slot types (`SMALL`, `MEDIUM`, `LARGE`), floors, and occupancy status.
    *   [Ticket.java](./model/Ticket.java): Manages active/closed ticket transactions, parking fees, and entry/exit timestamps.
*   **Repositories ([repository](./repository))**: Interfaces with the MySQL database using JDBC.
    *   [VehicleRepository.java](./repository/VehicleRepository.java): Handles persistence and lookup for vehicle records.
    *   [SlotRepository.java](./repository/SlotRepository.java): Manages parking slot occupancy updates and fetches available spots.
    *   [TicketRepository.java](./repository/TicketRepository.java): Creates and updates parking tickets.
*   **Services ([service](./service))**: Contains business logic orchestration.
    *   [EntryService.java](./service/EntryService.java): Coordinates assigning an available slot, saving the vehicle, and generating a ticket.
    *   [ExitService.java](./service/ExitService.java): Processes vehicle exits, calculates fees, updates ticket status, and frees up slots.
    *   [SlotService.java](./service/SlotService.java): Retrieves available parking slots by vehicle type.
*   **Utilities ([utils](./utils))**: Helper classes for system rules.
    *   [FeeCalculator.java](./utils/FeeCalculator.java): Calculates the hourly fee based on vehicle type rates (BIKE: 10/hr, CAR: 20/hr, TRUCK: 40/hr).
    *   [ParkingUtil.java](./utils/ParkingUtil.java): Handles validation logic, type matching (BIKE &rarr; SMALL slot, CAR &rarr; MEDIUM slot, TRUCK &rarr; LARGE slot), and ticket ID/timestamp generation.
*   **Configuration ([config](./config))**:
    *   [DBConnection.java](./config/DBConnection.java): Initializes database connection using JDBC.
    *   [InitializeDatabase.java](./config/InitializeDatabase.java): Creates the necessary tables and seeds default parking slots.

## Database Setup

The database runs in a MySQL 8.0 Docker container. The configuration is defined in [docker-compose.yml](./docker-compose.yml).

### Database Credentials
*   **Host**: `localhost`
*   **Port**: `3306`
*   **Database Name**: `parking_lot`
*   **Username**: `admin`
*   **Password**: `admin123`

---

## How to Setup and Run

### 1. Start the Database
Start the MySQL Docker container using docker compose:
```bash
docker compose up -d
```

### 2. Initialize the Database Schema
Compile and run the schema initialization script to set up tables and seed initial slots:
```bash
# Compile project classes
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" config/*.java model/*.java utils/*.java repository/*.java service/*.java

# Run database initializer
java -cp ".;lib/mysql-connector-j-9.6.0.jar" config.InitializeDatabase
```

### 3. Run the Application
Compile all source files and run the main entry point:
```bash
# Compile the main application
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" Main.java

# Run the system
java -cp ".;lib/mysql-connector-j-9.6.0.jar" Main
```

## System Features

1.  **Park Vehicle**: Accepts a vehicle number and type. Checks for active tickets, locates an appropriate slot, marks it occupied, and generates a parking ticket.
2.  **Exit Vehicle**: Input the ticket ID. It calculates the total parking fee, marks the ticket closed, registers the exit timestamp, and frees up the occupied slot.
3.  **Show Available Slots**: Lists the vacant parking slots based on a selected vehicle type.
