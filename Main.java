import java.util.List;
import java.util.Scanner;

import service.EntryService;
import service.ExitService;
import service.SlotService;
import model.ParkingSlot;
import model.Ticket;

public class Main {
  private static ExitService exitService = new ExitService();
  private static EntryService entryService = new EntryService();
  private static SlotService slotService = new SlotService();

  private static final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    while (true) {
      showMenu();

      int choice = Integer.parseInt(sc.nextLine());

      switch (choice) {
        case 1:
          handleParkVehicle();
          break;
        case 2:
          handleExitVehicle();
          break;
        case 3:
          handleShowAvailableSlots();
          break;
        case 4:
          System.out.println("Exiting system...");
          return;
        default:
          System.out.println("Invalid choice!");
      }
    }
  }

  private static void showMenu() {
    System.out.println("\n------ Parking Lot System ------");
    System.out.println("1. Park Vehicle");
    System.out.println("2. Exit Vehicle");
    System.out.println("3. Show Available Slots");
    System.out.println("4. Exit");
    System.out.print("Enter your choice: ");
  }

  private static void handleParkVehicle() {
    System.out.print("Enter vehicle number: ");
    String vehicleNumber = sc.nextLine().trim();

    System.out.print("Enter vehicle type (BIKE/CAR/TRUCK): ");
    String vehicleType = sc.nextLine().trim();

    try {
      Ticket ticket = entryService.parkVehicle(vehicleNumber, vehicleType);

      System.out.println("\n------ Ticket Generated ------");
      System.out.println("Ticket ID: " + ticket.getTicketId());
      System.out.println("Slot ID: " + ticket.getSlotId());
      System.out.println("Entry Time: " + ticket.getEntryTime());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void handleExitVehicle() {
    System.out.print("Enter ticket ID: ");
    String ticketId = sc.nextLine().trim();

    try {
      Ticket ticket = exitService.exitVehicle(ticketId);

      System.out.println("\n------ Exit Successful ------");
      System.out.println("Ticket ID: " + ticket.getTicketId());
      System.out.println("Exit Time: " + ticket.getExitTime());
      System.out.println("Charges: " + ticket.getFee());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void handleShowAvailableSlots() {
    try {
      System.out.print("Enter vehicle type (BIKE/CAR/TRUCK): ");
      String vehicleType = sc.nextLine().trim();

      List<ParkingSlot> availableSlots = slotService.getAvailableSlots(vehicleType);

      if (!availableSlots.isEmpty()) {
        System.out.println("\n------ Available Slots ------");
        for (ParkingSlot slot : availableSlots) {
          System.out.println("[" + slot.getSlotId() + "] " + slot.getSlotNumber() + " (Floor " + slot.getFloor() + ")");
        }
      }else{
        System.out.println("No slots available");
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
