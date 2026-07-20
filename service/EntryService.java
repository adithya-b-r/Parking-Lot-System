package service;

import model.ParkingSlot;
import model.Ticket;
import model.Vehicle;

import java.sql.Timestamp;

import repository.SlotRepository;
import repository.TicketRepository;
import repository.VehicleRepository;
import utils.ParkingUtil;

public class EntryService {
  TicketRepository ticketRepository = new TicketRepository();
  VehicleRepository vehicleRepository = new VehicleRepository();
  SlotRepository slotRepository = new SlotRepository();

  public Ticket parkVehicle(String vehicleNumber, String vehicleType) {
    if (vehicleNumber == null || vehicleNumber.trim().length() < 4 || !vehicleNumber.matches("^[a-zA-Z0-9-]+$")) {
      throw new RuntimeException("Invalid vehicle number");
    }

    if (vehicleType == null || !ParkingUtil.isValidVehicleType(vehicleType)) {
      throw new RuntimeException("Invalid vehicle type");
    }

    vehicleNumber = vehicleNumber.toUpperCase();
    vehicleType = vehicleType.toUpperCase();

    if (ticketRepository.getActiveTicketByVehicleNumber(vehicleNumber) != null) {
      throw new RuntimeException("Vehicle already parked");
    }

    vehicleRepository.saveVehicle(new Vehicle(vehicleNumber, vehicleType));

    String slotType = ParkingUtil.getSlotType(vehicleType);
    ParkingSlot parkingSlot = slotRepository.findAvailableSlot(slotType);

    if (parkingSlot == null) {
      throw new RuntimeException("Parking full for this vehicle type");
    }

    String ticketId = ParkingUtil.generateTicketId();
    Timestamp entryTime = ParkingUtil.getTimestamp();

    Ticket ticket = new Ticket(ticketId, vehicleNumber, parkingSlot.getSlotId(), entryTime, null, "ACTIVE");

    ticketRepository.createTicket(ticket);
    slotRepository.markSlotOccupied(parkingSlot.getSlotId());

    return ticket;
  }

  public static void main(String[] args) {
    EntryService entryService = new EntryService();

    Ticket ticket = entryService.parkVehicle("AAAAA-A", "CAR");

    System.out.println(
        ticket.getTicketId() + "," +
            ticket.getVehicleNumber() + "," +
            ticket.getSlotId() + "," +
            ticket.getEntryTime() + "," +
            ticket.getExitTime() + "," +
            ticket.getStatus());
  }
}