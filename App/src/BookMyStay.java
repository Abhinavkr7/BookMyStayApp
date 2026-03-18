import java.util.*;
import java.util.concurrent.*;

/**
 * Manages room inventory counts.
 */
class BookMyStay {
    private final Map<String, Integer> inventory = new HashMap<>();

    public BookMyStay() {
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    // Thread-safe inventory check
    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    // Thread-safe decrement
    public synchronized boolean bookRoom(String roomType) {
        if (isAvailable(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            return true;
        }
        return false;
    }

    // Thread-safe increment (used for cancellations if needed)
    public synchronized void incrementRoomCount(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public synchronized void printInventoryStatus() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * Represents a booking request
 */
class BookingRequest {
    String reservationId;
    String roomType;

    public BookingRequest(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

/**
 * Handles booking requests concurrently
 */
class BookingProcessor implements Runnable {
    private final Queue<BookingRequest> bookingQueue;
    private final BookMyStay inventory;
    private final Set<String> confirmedBookings; // Thread-safe

    public BookingProcessor(Queue<BookingRequest> bookingQueue, BookMyStay inventory, Set<String> confirmedBookings) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.confirmedBookings = confirmedBookings;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                request = bookingQueue.poll();
            }

            if (request != null) {
                boolean booked = inventory.bookRoom(request.roomType);
                if (booked) {
                    confirmedBookings.add(request.reservationId);
                    System.out.println(Thread.currentThread().getName() + " booked " + request.reservationId + " (" + request.roomType + ")");
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to book " + request.reservationId + " (" + request.roomType + ") - No availability");
                }
            }
        }
    }
}

/**
 * Main class to simulate concurrent bookings
 */
 class BookMyStayConcurrent {
    static void main(String[] args) throws InterruptedException {
        BookMyStay inventory = new BookMyStay();
        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        Set<String> confirmedBookings = Collections.synchronizedSet(new HashSet<>());

        // Generate booking requests
        bookingQueue.add(new BookingRequest("R101", "Single"));
        bookingQueue.add(new BookingRequest("R102", "Double"));
        bookingQueue.add(new BookingRequest("R103", "Suite"));
        bookingQueue.add(new BookingRequest("R104", "Single"));
        bookingQueue.add(new BookingRequest("R105", "Double"));
        bookingQueue.add(new BookingRequest("R106", "Single"));

        // Create multiple threads simulating concurrent guests
        int numberOfThreads = 3;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new BookingProcessor(bookingQueue, inventory, confirmedBookings), "GuestThread-" + (i + 1));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nAll bookings processed.\nConfirmed bookings: " + confirmedBookings);
        inventory.printInventoryStatus();
    }
}