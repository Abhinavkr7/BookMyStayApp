```java
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoomAllocationService {

    /**
     * Stores all allocated room IDs
     * to prevent duplicate assignments.
     */
    private Set<String> allocatedRoomIds;

    /**
     * Stores assigned room IDs by room type
     * Key   -> Room type
     * Value -> Set of assigned room IDs
     */
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     *
     * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Ensure uniqueness
        while (allocatedRoomIds.contains(roomId)) {
            roomId = generateRoomId(roomType);
        }

        // Store allocated room ID
        allocatedRoomIds.add(roomId);

        // Track assigned rooms by type
        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.updateAvailability(roomType, available - 1);

        // Confirm reservation
        System.out.println("Reservation confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + roomId);
    }

    /**
     * Generates a unique room ID.
     */
    private String generateRoomId(String roomType) {

        int random = (int) (Math.random() * 1000);
        return roomType.substring(0, 2).toUpperCase() + "-" + random;
    }

    /**
     * Displays all allocated rooms.
     */
    public void displayAllocatedRooms() {

        System.out.println("\n===== Allocated Rooms =====");

        for (Map.Entry<String, Set<String>> entry : assignedRoomsByType.entrySet()) {

            System.out.println("Room Type: " + entry.getKey());

            for (String id : entry.getValue()) {
                System.out.println("  Room ID: " + id);
            }
        }
    }
}
```
```java
public class RoomSearchService {

    /**
     * Displays available rooms with details and pricing.
     * This method only reads data and does NOT modify inventory.
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("\n===== Available Rooms =====");

        int singleAvailable = inventory.getAvailability("SingleRoom");
        if (singleAvailable > 0) {
            System.out.println("\nSingle Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + singleAvailable);
        }

        int doubleAvailable = inventory.getAvailability("DoubleRoom");
        if (doubleAvailable > 0) {
            System.out.println("\nDouble Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + doubleAvailable);
        }

        int suiteAvailable = inventory.getAvailability("SuiteRoom");
        if (suiteAvailable > 0) {
            System.out.println("\nSuite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + suiteAvailable);
        }

        System.out.println("\n============================");
    }
}
```
