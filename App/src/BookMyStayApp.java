```java
import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    // Constants for room types
    public static final String SINGLE = "SingleRoom";
    public static final String DOUBLE = "DoubleRoom";
    public static final String SUITE  = "SuiteRoom";

    /**
     * Stores available room count for each room type
     * Key   -> Room type
     * Value -> Available count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes inventory
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes default room availability
     */
    private void initializeInventory() {
        roomAvailability.put(SINGLE, 5);
        roomAvailability.put(DOUBLE, 3);
        roomAvailability.put(SUITE, 2);
    }

    /**
     * Returns available room count
     */
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    /**
     * Book a room if available
     */
    public boolean bookRoom(String roomType) {

        int available = getAvailability(roomType);

        if (available > 0) {
            roomAvailability.put(roomType, available - 1);
            System.out.println("Room booked successfully.");
            return true;
        } else {
            System.out.println("No rooms available for " + roomType);
            return false;
        }
    }

    /**
     * Cancel booking and return room to inventory
     */
    public void cancelBooking(String roomType) {

        int available = getAvailability(roomType);
        roomAvailability.put(roomType, available + 1);

        System.out.println("Booking cancelled. Room returned to inventory.");
    }

    /**
     * Display complete inventory
     */
    public void displayInventory() {

        System.out.println("\n===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms available");
        }

        System.out.println("==================================");
    }
}
```
