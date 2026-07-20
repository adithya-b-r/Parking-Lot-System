package repository;

import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import utils.ParkingUtil;

public class VehicleRepository {

  public void saveVehicle(Vehicle vehicle) {
    if (vehicleExists(vehicle.getVehicleNumber())) {
      return;
    }

    if (!ParkingUtil.isValidVehicleType(vehicle.getVehicleType())) {
      throw new RuntimeException("Invalid vehicle type");
    }

    String sql = "INSERT INTO vehicles(vehicle_number, vehicle_type) VALUES(?, ?)";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {
      ps.setString(1, vehicle.getVehicleNumber());
      ps.setString(2, vehicle.getVehicleType());

      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Error while saving the vehicle", e);
    }
  }

  public Vehicle findVehicleByNumber(String vehicleNumber) {
    String sql = "SELECT * FROM vehicles WHERE vehicle_number=?";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {
      ps.setString(1, vehicleNumber);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Vehicle(rs.getString("vehicle_number"), rs.getString("vehicle_type"));
        }
      }

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error while finding the vehicle", e);
    }
  }

  public boolean vehicleExists(String vehicleNumber) {
    String sql = "SELECT 1 FROM vehicles WHERE vehicle_number=?";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {

      ps.setString(1, vehicleNumber);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return true;
        }
      }

      return false;
    } catch (Exception e) {
      throw new RuntimeException("Error while checking vehicle existence", e);
    }
  }
}