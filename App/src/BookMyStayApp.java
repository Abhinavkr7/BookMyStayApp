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

        // Check Single Room availability
        int singleAvailable = inventory.getAvailability("SingleRoom");
        if (singleAvailable > 0) {
            System.out.println("\nSingle Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + singleAvailable);
        }

        // Check Double Room availability
        int doubleAvailable = inventory.getAvailability("DoubleRoom");
        if (doubleAvailable > 0) {
            System.out.println("\nDouble Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + doubleAvailable);
        }

        // Check Suite Room availability
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
