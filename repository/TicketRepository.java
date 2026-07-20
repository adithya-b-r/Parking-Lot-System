package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.List;
import java.util.ArrayList;

import config.DBConnection;
import model.Ticket;

public class TicketRepository {
  public void createTicket(Ticket ticket) {
    String sql = "INSERT INTO tickets(vehicle_number, slot_id, entry_time, status, ticket_id)VALUES(?,?,?,?,?)";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {
          ps.setString(1, ticket.getVehicleNumber());
          ps.setInt(2, ticket.getSlotId());
          ps.setTimestamp(3, ticket.getEntryTime());
          ps.setString(4, ticket.getStatus());
          ps.setString(5, ticket.getTicketId());

      ps.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Error while creating a ticket.", e);
    }
  }

  public Ticket getTicketById(String ticketId) {
    String sql = "SELECT * FROM tickets WHERE ticket_id=? LIMIT 1";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {

      ps.setString(1, ticketId);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Ticket(rs.getString("ticket_id"), rs.getString("vehicle_number"), rs.getInt("slot_id"),
              rs.getTimestamp("entry_time"), rs.getTimestamp("exit_time"), rs.getString("status"));
        }
      }

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error while retrieving the ticket.", e);
    }
  }

  public Ticket getActiveTicketByVehicleNumber(String vehicleNumber) {
    String sql = "SELECT * FROM tickets WHERE vehicle_number=? AND status='ACTIVE' LIMIT 1";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {

      ps.setString(1, vehicleNumber);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new Ticket(rs.getString("ticket_id"), rs.getString("vehicle_number"), rs.getInt("slot_id"),
              rs.getTimestamp("entry_time"), rs.getTimestamp("exit_time"), rs.getString("status"));
        }
      }

      return null;
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving ticket by vehicle number.", e);
    }
  }

  public void closeTicket(String ticketId, Timestamp exitTime) {
    String sql = "UPDATE tickets SET exit_time=?, status='CLOSED' WHERE ticket_id=? AND status='ACTIVE'";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {

      ps.setTimestamp(1, exitTime);
      ps.setString(2, ticketId);

      int rows = ps.executeUpdate();

      if(rows == 0){
        throw new RuntimeException("Ticket does'nt exists.");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while closing the ticket.", e);
    }
  }

  public List<Ticket> getAllActiveTickets() {
    List<Ticket> list = new ArrayList<>();

    String sql = "SELECT * FROM tickets WHERE status='ACTIVE' ORDER BY entry_time";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();) {

      while (rs.next()) {
        list.add(
            new Ticket(
                rs.getString("ticket_id"),
                rs.getString("vehicle_number"),
                rs.getInt("slot_id"),
                rs.getTimestamp("entry_time"),
                rs.getTimestamp("exit_time"),
                rs.getString("status")));
      }

      return list;
    } catch (Exception e) {
      throw new RuntimeException("Error while retrieving active tickets.", e);
    }
  }

}