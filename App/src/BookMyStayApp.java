```java
import java.util.Map;

public class RoomSearchService {

    /**
     * Displays available rooms along with
     * their details and pricing.
     *
     * This method performs read-only access
     * to inventory and room data.
     *
     * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("\n===== Available Rooms =====");

        // Check and display Single Room availability
        if (availability.get("SingleRoom") > 0) {
            System.out.println("\nSingle Room Available:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("SingleRoom"));
        }

        // Check and display Double Room availability
        if (availability.get("DoubleRoom") > 0) {
            System.out.println("\nDouble Room Available:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("DoubleRoom"));
        }

        // Check and display Suite Room availability
        if (availability.get("SuiteRoom") > 0) {
            System.out.println("\nSuite Room Available:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("SuiteRoom"));
        }

        System.out.println("\n===========================");
    }
}
```
