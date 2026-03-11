```java
// Abstract class representing a generic hotel room
abstract class Room {

    /** Number of beds available in the room */
    protected int numberOfBeds;

    /** Total size of the room in square feet */
    protected int squareFeet;

    /** Price charged per night for this room type */
    protected double pricePerNight;

    // Constructor used by child classes
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Room Size: " + squareFeet + " sq ft");
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 1000);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 1800);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 3500);
    }
}

// Main Application
public class BookMyStayApp {

    public static void main(String[] args) {

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("===== Hotel Room Availability =====\n");

        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability);
        System.out.println();

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability);
        System.out.println();

        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability);
        System.out.println();

        System.out.println("Application Terminated.");
    }
}
```
