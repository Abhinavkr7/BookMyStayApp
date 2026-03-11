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
