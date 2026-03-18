import java.util.*;

// 1. Renamed this class to 'Reservation' to avoid conflict with the Main class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    // Constructor name must match class name
    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest: " + guestName + ", Room Type: " + roomType;
    }
}

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() { return serviceName + " (₹" + cost + ")"; }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }
        System.out.println("Add-On Services:");
        for (AddOnService service : services) {
            System.out.println(" - " + service);
        }
    }
}

// 2. This name now matches your filename: BookMySayApp.java
public class BookMyStayApp {

    public static void main(String[] args) {
        Reservation reservation = new Reservation("R101", "Arun", "Deluxe");
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        serviceManager.addService(reservation.getReservationId(), new AddOnService("Breakfast", 500));
        serviceManager.addService(reservation.getReservationId(), new AddOnService("Spa Access", 2000));

        System.out.println("=== Reservation Details ===");
        System.out.println(reservation);

        System.out.println("\n=== Selected Add-On Services ===");
        serviceManager.displayServices(reservation.getReservationId());

        double totalCost = serviceManager.calculateTotalServiceCost(reservation.getReservationId());
        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}