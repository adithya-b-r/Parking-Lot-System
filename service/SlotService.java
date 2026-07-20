package service;

import java.util.List;

import model.ParkingSlot;
import utils.ParkingUtil;
import repository.SlotRepository;

public class SlotService {
  private SlotRepository slotRepository = new SlotRepository();

  public List<ParkingSlot> getAvailableSlots(String vehicleType) {
    try {
      String slotType = ParkingUtil.getSlotType(vehicleType);

      return slotRepository.getAvailableSlotsByType(slotType);
    } catch (Exception e) {
      throw new RuntimeException("Error while finding available slots");
    }
  }
}
