import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

    
        AuditLog auditLog = new AuditLog();

        
        Room room1 = new Room(101, "Single", "Available", 50.0, "Single Bed");
        Room room2 = new Room(102, "Double", "Available", 100.0, "Double Bed");
        List<Room> rooms = new ArrayList<>(Arrays.asList(room1, room2));

        
        System.out.println("Enter Guest Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Passport or National ID: ");
        String passportOrNationalId = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Guest guest = new Guest(new Random().nextInt(1000), name, contactInfo, passportOrNationalId, email);
        auditLog.log("Guest " + guest.getName() + " added with ID " + guest.getGuestId());

        
        System.out.println("Select Room Type:");
        System.out.println("1. Single Bed ($50 per night)");
        System.out.println("2. Double Bed ($100 per night)");
        String roomChoice = scanner.nextLine();

        Room selectedRoom;
        if (roomChoice.equals("1")) {
            selectedRoom = room1;
        } else if (roomChoice.equals("2")) {
            selectedRoom = room2;
        } else {
            System.out.println("Invalid room type selection.");
            return;
        }

        synchronized (selectedRoom) {
            if (!"Available".equals(selectedRoom.getRoomStatus())) {
                System.out.println("Room is not available.");
                return;
            }
            selectedRoom.setRoomStatus("In Progress");
            auditLog.log("Room " + selectedRoom.getRoomId() + " is locked for booking.");
        }

        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Enter Check-In Date (dd/MM/yyyy): ");
        Date checkIn = sdf.parse(scanner.nextLine());
        System.out.print("Enter Check-Out Date (dd/MM/yyyy): ");
        Date checkOut = sdf.parse(scanner.nextLine());

        Reservation reservation = new Reservation(new Random().nextInt(1000), selectedRoom.getRoomId(),
                guest.getGuestId(), checkIn, checkOut, "Pending");

        
        selectedRoom.setRoomStatus("Reserved");
        auditLog.log("Room " + selectedRoom.getRoomId() + " reserved for Guest " + guest.getName());

        
        List<Food> menu = Arrays.asList(
                new Food("Soup", 5.0),
                new Food("Pasta", 8.0),
                new Food("Pizza", 10.0),
                new Food("Salad", 6.0)
        );
        List<Food> selectedFood = new ArrayList<>();

        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
        System.out.println("Enter the numbers of the food items you want (comma-separated):");
        String[] foodChoices = scanner.nextLine().split(",");
        for (String choice : foodChoices) {
            selectedFood.add(menu.get(Integer.parseInt(choice.trim()) - 1));
        }

    
        Invoice invoice = new Invoice(new Random().nextInt(1000), reservation.getReservationId(), 5.0, 15.0);
        double roomCost = invoice.generateInvoice(reservation, selectedRoom);
        double foodCost = selectedFood.stream().mapToDouble(Food::getPrice).sum();
        double totalCost = roomCost + foodCost;

        
        System.out.println("\n=== Invoice ===");
        System.out.println("Room Cost: $" + (roomCost - invoice.getTaxAmount() - invoice.getServiceFee()));
        System.out.println("Tax Amount: $" + invoice.getTaxAmount());
        System.out.println("Service Fee: $" + invoice.getServiceFee());
        System.out.println("Food Cost: $" + foodCost);
        System.out.println("Total Cost: $" + totalCost);

        
        PaymentService paymentService = new PaymentService();
        if (paymentService.processPayment(totalCost)) {
            reservation.setPaymentStatus("Completed");
            auditLog.log("Payment of $" + totalCost + " completed for Reservation " + reservation.getReservationId());

            
            selectedRoom.setRoomStatus("Available");
            auditLog.log("Room " + selectedRoom.getRoomId() + " is now available after Guest " + guest.getName() + "'s checkout.");
        } else {
            auditLog.log("Payment failed for Reservation " + reservation.getReservationId());
            System.out.println("Payment failed. Booking is canceled.");
            selectedRoom.setRoomStatus("Available");
            return;
        }

        
        auditLog.showLogs();

        scanner.close();
    }
}
