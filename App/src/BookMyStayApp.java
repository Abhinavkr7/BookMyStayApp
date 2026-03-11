```java
import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    /**
     * Stores available room count for each room type.
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     * This method centralizes inventory setup
     * instead of using scattered variables.
     */
    private void initializeInventory() {
        roomAvailability.put("SingleRoom", 5);
        roomAvailability.put("DoubleRoom", 3);
        roomAvailability.put("SuiteRoom", 2);
    }

    /**
     * Returns available rooms for a given room type.
     */
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    /**
     * Updates room availability for a given type.
     */
    public void updateAvailability(String roomType, int newCount) {
        if (roomAvailability.containsKey(roomType)) {
            roomAvailability.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    /**
     * Displays the entire inventory.
     */
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}
```
