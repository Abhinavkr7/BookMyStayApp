
public class Reservation {

    /** Name of the guest making the booking */
    private String guestName;

    /** Requested room type */
    private String roomType;

    /**
     * Creates a new booking request
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** Returns guest name */
    public String getGuestName() {
        return guestName;
    }

    /** Returns requested room type */
    public String getRoomType() {
        return roomType;
    }

    /** Display reservation request */
    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
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
