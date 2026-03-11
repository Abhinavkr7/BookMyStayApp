```java
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
