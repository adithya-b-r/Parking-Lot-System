package service;

import java.sql.Timestamp;

import model.Ticket;
import model.Vehicle;
import repository.SlotRepository;
import repository.TicketRepository;
import repository.VehicleRepository;
import utils.FeeCalculator;
import utils.ParkingUtil;

public class ExitService {
  private SlotRepository slotRepository = new SlotRepository();
  private TicketRepository ticketRepository = new TicketRepository();
  private VehicleRepository vehicleRepository = new VehicleRepository();
  private FeeCalculator feeCalculator = new FeeCalculator();

  public Ticket exitVehicle(String ticketId) {

    if (ticketId == null || ticketId.trim().isEmpty()) {
      throw new RuntimeException("Invalid ticket ID");
    }

    Ticket ticket = ticketRepository.getTicketById(ticketId);

    if (ticket == null) {
      throw new RuntimeException("Invalid ticket ID");
    }

    if ("CLOSED".equals(ticket.getStatus())) {
      throw new RuntimeException("Vehicle already exited");
    }

    Vehicle vehicle = vehicleRepository.findVehicleByNumber(ticket.getVehicleNumber());
    if (vehicle == null) {
      throw new RuntimeException("Invalid vehicle");
    }

    Timestamp exitTime = ParkingUtil.getTimestamp();

    double fee = feeCalculator.calculateFee(
        ticket.getEntryTime(),
        exitTime,
        vehicle.getVehicleType()
    );

    ticketRepository.closeTicket(ticketId, exitTime);
    slotRepository.markSlotFree(ticket.getSlotId());

    ticket.setExitTime(exitTime);
    ticket.setFee(fee);

    return ticket;
  }
}