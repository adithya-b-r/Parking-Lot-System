package utils;

import java.sql.Timestamp;

enum Pricing {
  BIKE(10), CAR(20), TRUCK(40);

  private final int price;

  Pricing(int price) {
    this.price = price;
  }

  public int getPrice() {
    return price;
  }
}

public class FeeCalculator {
  public double calculateFee(Timestamp entryTime, Timestamp exitTime, String vehicleType) {
    double fee = 0;

    if (entryTime == null || exitTime == null) {
      throw new RuntimeException("Invalid timestamp");
    } else if (entryTime.compareTo(exitTime) > 0) {
      throw new RuntimeException("Exit time can't be less than the entry time");
    } else if (!ParkingUtil.isValidVehicleType(vehicleType)) {
      throw new RuntimeException("Invalid vehicle type");
    }

    vehicleType = vehicleType.toUpperCase();

    long ms = exitTime.getTime() - entryTime.getTime();
    int hours = (int) Math.ceil(ms / (1000.0 * 60 * 60));
    int price = Pricing.valueOf(vehicleType).getPrice();

    fee = price * Math.max(1, hours);

    return fee;
  }
}
