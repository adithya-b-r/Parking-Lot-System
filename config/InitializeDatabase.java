package config;

import config.DBConnection;
import java.sql.Connection;
import java.sql.Statement;

public class InitializeDatabase {
  public static void main(String[] args) throws Exception {
    Connection conn = DBConnection.getConnection();

    Statement stmt = conn.createStatement();

    try{
    String vehicles = "CREATE TABLE IF NOT EXISTS vehicles(" +
        "vehicle_number VARCHAR(15) PRIMARY KEY," +
        "vehicle_type ENUM('BIKE', 'CAR', 'TRUCK') NOT NULL" +
        ") ENGINE=InnoDB";

    stmt.executeUpdate(vehicles);

    String tickets = "CREATE TABLE IF NOT EXISTS tickets(\r\n" + //
            "    ticket_id VARCHAR(30) PRIMARY KEY,\r\n" + //
            "    vehicle_id INT NOT NULL,\r\n" + //
            "    slot_id INT NOT NULL,\r\n" + //
            "    entry_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + //
            "    exit_time TIMESTAMP NULL,\r\n" + //
            "    status ENUM('ACTIVE', 'CLOSED') NOT NULL DEFAULT 'ACTIVE',\r\n" + //
            "    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),\r\n" + //
            "    FOREIGN KEY (slot_id) REFERENCES parking_slots(slot_id)\r\n" + //
            ") ENGINE=InnoDB;";

    stmt.executeUpdate(tickets);

    String parking_slots = "CREATE TABLE IF NOT EXISTS parking_slots(" +
        "slot_id INT AUTO_INCREMENT PRIMARY KEY," +
        "slot_number VARCHAR(10) NOT NULL UNIQUE," +
        "slot_type ENUM('SMALL','MEDIUM','LARGE') NOT NULL," +
        "floor INT NOT NULL," +
        "is_occupied BOOLEAN NOT NULL DEFAULT FALSE" +
        ") ENGINE=InnoDB";

    stmt.executeUpdate(parking_slots);

    String insertSlots = "INSERT IGNORE INTO parking_slots(slot_number, slot_type, floor) VALUES"+
      "('S1','SMALL',1),('S2','SMALL',1),('S3','SMALL',1),"+
      "('M1','MEDIUM',1),('M2','MEDIUM',1),('M3','MEDIUM',1),"+
      "('L1','LARGE',1),('L2','LARGE',1),('L3','LARGE',1)";

    stmt.executeUpdate(insertSlots);
    }catch(Exception e){
      System.out.println("Error: "+e.getMessage());
    }

    stmt.close();
    conn.close();
  }
}
