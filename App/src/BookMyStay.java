import java.util.*;

/**
 * Manages room inventory counts.
 */
class BookMyStay {
    private Map<String, Integer> inventory = new HashMap<>();

    public BookMyStay() {
        // Initialize inventory counts for room types
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    public void incrementRoomCount(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
        System.out.println("Inventory updated: " + roomType + " count incremented.");
    }

    public void printInventoryStatus() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * Service for managing booking cancellations and rollback.
 */
class CancellationService {
    private Stack<String> releasedRoomIds; // Tracks rollback order
    private Map<String, String> reservationRoomTypeMap; // Maps reservation ID to room type

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    // Register a confirmed booking
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
        System.out.println("Booking registered: " + reservationId + " (" + roomType + ")");
    }

    // Cancel a booking and rollback inventory
    public void cancelBooking(String reservationId, BookMyStay inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation ID " + reservationId + " not found or already cancelled.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);
        releasedRoomIds.push(reservationId); // Record rollback
        inventory.incrementRoomCount(roomType); // Restore inventory
        reservationRoomTypeMap.remove(reservationId); // Mark cancelled

        System.out.println("Cancelled reservation: " + reservationId);
    }

    // Show rollback history
    public void showRollbackHistory() {
        System.out.println("Rollback History (most recent cancellations):");
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println(" - " + releasedRoomIds.get(i));
        }
    }
}

/**
 * MAIN CLASS
 * Demonstrates booking cancellation and inventory rollback.
 */
 class BookMyStayApp {
    static void main(String[] args) {
        BookMyStay inventory = new BookMyStay();
        CancellationService cancellationService = new CancellationService();

        // Register bookings
        cancellationService.registerBooking("R101", "Single");
        cancellationService.registerBooking("R102", "Double");
        cancellationService.registerBooking("R103", "Suite");

        System.out.println();

        // Cancel bookings
        cancellationService.cancelBooking("R102", inventory);
        cancellationService.cancelBooking("R999", inventory); // Invalid cancellation
        cancellationService.cancelBooking("R101", inventory);

        System.out.println();

        // Show rollback history
        cancellationService.showRollbackHistory();

        System.out.println();

        // Print current inventory status
        inventory.printInventoryStatus();
    }
}