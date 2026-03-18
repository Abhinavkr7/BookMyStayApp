import java.time.LocalDate;
import java.util.*;

public class BookMyStayApp {

    // -------------------- Reservation --------------------
    static class Reservation {
        private String bookingId;
        private String customerName;
        private String roomType;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;

        public Reservation(String bookingId, String customerName, String roomType,
                           LocalDate checkInDate, LocalDate checkOutDate) {
            this.bookingId = bookingId;
            this.customerName = customerName;
            this.roomType = roomType;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }

        public String getRoomType() {
            return roomType;
        }

        @Override
        public String toString() {
            return "BookingID: " + bookingId +
                    ", Customer: " + customerName +
                    ", Room: " + roomType +
                    ", Check-In: " + checkInDate +
                    ", Check-Out: " + checkOutDate;
        }
    }

    // -------------------- Booking History --------------------
    static class BookingHistory {
        private List<Reservation> reservations = new ArrayList<>();

        public void addReservation(Reservation reservation) {
            reservations.add(reservation);
        }

        public List<Reservation> getAllReservations() {
            return Collections.unmodifiableList(reservations);
        }
    }

    // -------------------- Report Service --------------------
    static class BookingReportService {

        public void printAllBookings(List<Reservation> reservations) {
            System.out.println("\n--- Booking History ---");
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }

        public void printTotalBookings(List<Reservation> reservations) {
            System.out.println("\nTotal Bookings: " + reservations.size());
        }

        public void printRoomTypeSummary(List<Reservation> reservations) {
            Map<String, Integer> map = new HashMap<>();

            for (Reservation r : reservations) {
                map.put(r.getRoomType(),
                        map.getOrDefault(r.getRoomType(), 0) + 1);
            }

            System.out.println("\n--- Room Type Summary ---");
            for (String type : map.keySet()) {
                System.out.println(type + ": " + map.get(type));
            }
        }
    }

    // -------------------- Main Method --------------------
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        history.addReservation(new Reservation(
                "B101", "Alice", "Deluxe",
                LocalDate.of(2026, 3, 20),
                LocalDate.of(2026, 3, 22)
        ));

        history.addReservation(new Reservation(
                "B102", "Bob", "Standard",
                LocalDate.of(2026, 3, 21),
                LocalDate.of(2026, 3, 23)
        ));

        history.addReservation(new Reservation(
                "B103", "Charlie", "Deluxe",
                LocalDate.of(2026, 3, 22),
                LocalDate.of(2026, 3, 25)
        ));

        // Generate reports
        reportService.printAllBookings(history.getAllReservations());
        reportService.printTotalBookings(history.getAllReservations());
        reportService.printRoomTypeSummary(history.getAllReservations());
    }
}