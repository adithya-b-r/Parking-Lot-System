package repository;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import model.ParkingSlot;
import utils.ParkingUtil;

public class SlotRepository {
  public ParkingSlot findAvailableSlot(String slotType) {
    if (!ParkingUtil.isValidSlotType(slotType)) {
      throw new RuntimeException("Invalid slot type");
    }

    String sql = "SELECT * FROM parking_slots WHERE slot_type=? AND is_occupied=FALSE ORDER BY slot_id ASC LIMIT 1";

    try (Connection conn = DBConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, slotType);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new ParkingSlot(
            rs.getInt("slot_id"),
            rs.getString("slot_number"),
            rs.getString("slot_type"),
            rs.getInt("floor"),
            rs.getBoolean("is_occupied"));
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException("Error finding available slot", e);
    }
  }

  public List<ParkingSlot> getAvailableSlotsByType(String slotType) {
    if (!ParkingUtil.isValidSlotType(slotType)) {
      throw new RuntimeException("Invalid slot type");
    }

    List<ParkingSlot> list = new ArrayList<>();
    String sql = "SELECT * FROM parking_slots WHERE slot_type=? AND is_occupied=FALSE ORDER BY slot_id ASC";

    try (Connection conn = DBConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, slotType);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        list.add(new ParkingSlot(
            rs.getInt("slot_id"),
            rs.getString("slot_number"),
            rs.getString("slot_type"),
            rs.getInt("floor"),
            rs.getBoolean("is_occupied")));
      }
    } catch (Exception e) {
      throw new RuntimeException("Error viewing all parking slots", e);
    }
    return list;
  }

  public void markSlotOccupied(int slotId) {
    String sql = "UPDATE parking_slots SET is_occupied=TRUE WHERE slot_id=?";

    try (Connection conn = DBConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, slotId);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Error marking slot occupied", e);
    }
  }

  public void markSlotFree(int slotId) {
    String sql = "UPDATE parking_slots SET is_occupied=FALSE WHERE slot_id=?";

    try (Connection conn = DBConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, slotId);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Error marking slot free", e);
    }
  }
}