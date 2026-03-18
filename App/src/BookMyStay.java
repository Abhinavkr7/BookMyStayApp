import java.util.*;

// Main Application (Single File)
public class BookMyStay {

    // Room Inventory (shared system state)
    private static Map<String, Integer> roomInventory = new HashMap<>();

    // Initialize inventory
    static {
        roomInventory.put("STANDARD", 5);
        roomInventory.put("DELUXE", 3);
        roomInventory.put("SUITE", 2);
    }

    // Custom Exception for Booking Errors
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Booking Validator
    static class BookingValidator {

        public static void validate(String roomType, int nights) throws InvalidBookingException {

            // Validate room type
            if (roomType == null || roomType.trim().isEmpty()) {
                throw new InvalidBookingException("Room type cannot be empty.");
            }

            if (!roomInventory.containsKey(roomType.toUpperCase())) {
                throw new InvalidBookingException("Invalid room type: " + roomType);
            }

            // Validate nights
            if (nights <= 0) {
                throw new InvalidBookingException("Number of nights must be greater than zero.");
            }

            // Validate availability (guard state)
            int available = roomInventory.get(roomType.toUpperCase());
            if (available <= 0) {
                throw new InvalidBookingException("No rooms available for type: " + roomType);
            }
        }
    }

    // Booking Service
    static class BookingService {

        public static void bookRoom(String guestName, String roomType, int nights) {
            try {
                // Fail-fast validation
                BookingValidator.validate(roomType, nights);

                roomType = roomType.toUpperCase();

                // Safe state update
                int available = roomInventory.get(roomType);

                if (available - 1 < 0) {
                    throw new InvalidBookingException("Booking would result in negative inventory.");
                }

                roomInventory.put(roomType, available - 1);

                System.out.println("Booking successful!");
                System.out.println("Guest: " + guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Nights: " + nights);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            } catch (Exception e) {
                // Catch unexpected errors
                System.out.println("Unexpected system error. Please try again.");
            }
        }
    }

    // Display Inventory
    static void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Main Method (Simulation)
    public static void main(String[] args) {

        displayInventory();

        System.out.println("\n--- Booking Attempts ---");

        // Valid booking
        BookingService.bookRoom("Alice", "STANDARD", 2);

        // Invalid room type
        BookingService.bookRoom("Bob", "PENTHOUSE", 1);

        // Invalid nights
        BookingService.bookRoom("Charlie", "DELUXE", 0);

        // Exhaust inventory
        BookingService.bookRoom("David", "SUITE", 1);
        BookingService.bookRoom("Eve", "SUITE", 1);
        BookingService.bookRoom("Frank", "SUITE", 1); // should fail

        displayInventory();
    }
}