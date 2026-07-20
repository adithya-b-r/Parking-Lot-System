package utils;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.UUID;

public class ParkingUtil {
  public static boolean isValidVehicleType(String vehicleType) {
    String valid[] = { "CAR", "BIKE", "TRUCK" };

    for (String v : valid) {
      if (v.equalsIgnoreCase(vehicleType)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isValidSlotType(String slotType) {
    String validSlots[] = { "SMALL", "MEDIUM", "LARGE" };

    for (String vs : validSlots) {
      if (vs.equalsIgnoreCase(slotType)) {
        return true;
      }
    }

    return false;
  }

  public static String getSlotType(String vehicleType){
    vehicleType = vehicleType.toUpperCase();

    if(vehicleType.equals("BIKE")){
      return "SMALL";
    }else if(vehicleType.equals("CAR")){
      return "MEDIUM";
    }else if(vehicleType.equals("TRUCK")){
      return "LARGE";
    }else{
      throw new RuntimeException("Invalid vehicleType");
    }
  }

  public static String generateTicketId() {
    return UUID.randomUUID().toString();
  }

  public static Timestamp getTimestamp() {
    LocalDateTime now = LocalDateTime.now();
    Timestamp tm = Timestamp.valueOf(now);

    return tm;
  }
}
