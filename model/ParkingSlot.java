package model;

public class ParkingSlot {
  private int slotId;
  private String slotNumber;
  private String slotType;
  private int floor;
  private boolean isOccupied;


  public ParkingSlot(int slotId, String slotNumber, String slotType, int floor, boolean isOccupied) {
    this.slotId = slotId;
    this.slotNumber = slotNumber;
    this.slotType = slotType;
    this.floor = floor;
    this.isOccupied = isOccupied;
  }

  public int getSlotId() {
    return slotId;
  }

  public String getSlotNumber() {
    return slotNumber;
  }

  public String getSlotType() {
    return slotType;
  }

  public int getFloor() {
    return floor;
  }

  public boolean isOccupied() {
    return isOccupied;
  }
}
