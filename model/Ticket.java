package model;

import java.sql.Timestamp;

public class Ticket {
  private String ticketId;
  private String vehicleNumber;

  private int slotId;
  private Timestamp entryTime;
  private Timestamp exitTime;
  private String status;
  private double fee;

  public Ticket(String ticketId, String vehicleNumber, int slotId, Timestamp entryTime, Timestamp exitTime,
      String status) {
    this.ticketId = ticketId;
    this.vehicleNumber = vehicleNumber;
    this.slotId = slotId;
    this.entryTime = entryTime;
    this.exitTime = exitTime;
    this.status = status;
  }

  public String getTicketId() {
    return ticketId;
  }

  public int getSlotId() {
    return slotId;
  }

  public Timestamp getEntryTime() {
    return entryTime;
  }

  public Timestamp getExitTime() {
    return exitTime;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public String getStatus() {
    return status;
  }

  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }

  public void setExitTime(Timestamp exitTime2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setExitTime'");
  }
}
