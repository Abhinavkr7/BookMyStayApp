import java.io.*;
import java.util.*;

// Main class for Book My Stay App
public class BookMyStay {

    // Serializable Booking class
    static class Booking implements Serializable {
        private static final long serialVersionUID = 1L;
        String guestName;
        int roomNumber;

        public Booking(String guestName, int roomNumber) {
            this.guestName = guestName;
            this.roomNumber = roomNumber;
        }

        @Override
        public String toString() {
            return "Booking{" + "guestName='" + guestName + '\'' + ", roomNumber=" + roomNumber + '}';
        }
    }

    // Serializable Inventory class
    static class Inventory implements Serializable {
        private static final long serialVersionUID = 1L;
        Map<Integer, Boolean> rooms = new HashMap<>(); // roomNumber -> availability

        public Inventory(int totalRooms) {
            for (int i = 1; i <= totalRooms; i++) {
                rooms.put(i, true); // all rooms available initially
            }
        }

        public boolean bookRoom(int roomNumber) {
            if (rooms.getOrDefault(roomNumber, false)) {
                rooms.put(roomNumber, false); // mark as booked
                return true;
            }
            return false;
        }

        public void releaseRoom(int roomNumber) {
            rooms.put(roomNumber, true);
        }

        @Override
        public String toString() {
            return "Inventory{" + "rooms=" + rooms + '}';
        }
    }

    // System state wrapper for persistence
    static class SystemState implements Serializable {
        private static final long serialVersionUID = 1L;
        List<Booking> bookings;
        Inventory inventory;

        public SystemState(List<Booking> bookings, Inventory inventory) {
            this.bookings = bookings;
            this.inventory = inventory;
        }
    }

    // Persistence Service
    static class PersistenceService {
        private static final String DATA_FILE = "system_state.ser";

        public static void save(SystemState state) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                oos.writeObject(state);
                System.out.println("System state saved successfully.");
            } catch (IOException e) {
                System.out.println("Failed to save system state: " + e.getMessage());
            }
        }

        public static SystemState load() {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                System.out.println("No saved state found. Starting fresh.");
                return null;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (SystemState) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load system state. Starting fresh. Error: " + e.getMessage());
                return null;
            }
        }
    }

    // Main application logic
    public static void main(String[] args) {
        // Load previous state if exists
        SystemState state = PersistenceService.load();
        List<Booking> bookings;
        Inventory inventory;

        if (state != null) {
            bookings = state.bookings;
            inventory = state.inventory;
        } else {
            bookings = new ArrayList<>();
            inventory = new Inventory(5); // Example: 5 rooms
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Book My Stay App ---");
            System.out.println("1. Show Inventory");
            System.out.println("2. Make Booking");
            System.out.println("3. Show Bookings");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println(inventory);
                    break;
                case "2":
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter room number to book: ");
                    int room = Integer.parseInt(scanner.nextLine());
                    if (inventory.bookRoom(room)) {
                        Booking booking = new Booking(name, room);
                        bookings.add(booking);
                        System.out.println("Booking successful: " + booking);
                    } else {
                        System.out.println("Room not available.");
                    }
                    break;
                case "3":
                    System.out.println("Current bookings:");
                    bookings.forEach(System.out::println);
                    break;
                case "4":
                    // Save state before exit
                    PersistenceService.save(new SystemState(bookings, inventory));
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}