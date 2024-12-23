import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            AuditLog auditLog = new AuditLog();
            Set<Integer> usedRoomIds = new HashSet<>();

           
            System.out.println("Enter Guest Details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();

            String contactInfo;
            while (true) {
                System.out.print("Contact Info (10 digits): ");
                contactInfo = scanner.nextLine();
                if (contactInfo.matches("\\d{10}")) {
                    break;
                } else {
                    System.out.println("Error: Contact number must be exactly 10 digits.");
                }
            }

            String email;
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Error: Invalid email format. Please enter a valid email.");
                }
            }

            System.out.print("Passport or National ID: ");
            String passportOrNationalId = scanner.nextLine();

            Guest guest = new Guest(new Random().nextInt(1000), name, contactInfo, passportOrNationalId, email);
            auditLog.log("Guest " + guest.getName() + " added with ID " + guest.getGuestId());

            List<Integer> selectedRoomIds = new ArrayList<>();
            double totalRoomCost = 0; 
            System.out.println("How many rooms would you like to book?");
            int roomCount = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < roomCount; i++) {
                Room selectedRoom = null;
                while (selectedRoom == null) {
                    System.out.println("Select Room Type for Room " + (i + 1) + ":");
                    System.out.println("1. Single Bed ($50 per night)");
                    System.out.println("2. Double Bed ($100 per night)");
                    String roomChoice = scanner.nextLine();

                    int roomId;
                    do {
                        roomId = new Random().nextInt(1000); 
                    } while (usedRoomIds.contains(roomId)); 
                    usedRoomIds.add(roomId);

                    if ("1".equals(roomChoice)) {
                        selectedRoom = new Room(roomId, "Single", "Available", 50.0, "Single Bed");
                        totalRoomCost += 50.0; 
                    } else if ("2".equals(roomChoice)) {
                        selectedRoom = new Room(roomId, "Double", "Available", 100.0, "Double Bed");
                        totalRoomCost += 100.0; 
                    } else {
                        System.out.println("Invalid room type selection. Please try again.");
                    }
                }

                synchronized (selectedRoom) {
                    if (!"Available".equals(selectedRoom.getRoomStatus())) {
                        System.out.println("Room is not available.");
                        return;
                    }
                    selectedRoom.setRoomStatus("In Progress");
                    auditLog.log("Room " + selectedRoom.getRoomId() + " is locked for booking.");
                }

                selectedRoomIds.add(selectedRoom.getRoomId());
            }

           
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date checkIn = null, checkOut = null;
            while (checkIn == null || checkOut == null) {
                try {
                    System.out.print("Enter Check-In Date (dd/MM/yyyy): ");
                    checkIn = sdf.parse(scanner.nextLine());
                    System.out.print("Enter Check-Out Date (dd/MM/yyyy): ");
                    checkOut = sdf.parse(scanner.nextLine());
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter again in dd/MM/yyyy format.");
                }
            }

            
            Reservation reservation = new Reservation(new Random().nextInt(1000), selectedRoomIds, guest.getGuestId(), checkIn, checkOut, "Pending");
            guest.addReservation(reservation);

            auditLog.log("Reservation created for Guest " + guest.getName() + " with Room IDs: " + selectedRoomIds);

            
            List<Food> orderedFoods = new ArrayList<>();
            double totalFoodCost = 0; 
            System.out.println("Would you like to order food? (yes/no): ");
            String orderFood = scanner.nextLine();
            if ("yes".equalsIgnoreCase(orderFood)) {
                List<Food> foodMenu = Arrays.asList(
                    new Food("Pizza", 15.0),
                    new Food("Burger", 8.0),
                    new Food("Pasta", 12.0),
                    new Food("Salad", 5.0),
                    new Food("Sushi", 20.0),
                    new Food("Steak", 25.0),
                    new Food("Fries", 3.0),
                    new Food("Ice Cream", 4.0)
                );
                System.out.println("Food Menu:");
                for (int i = 0; i < foodMenu.size(); i++) {
                    System.out.println((i + 1) + ". " + foodMenu.get(i));
                }
                System.out.println("Select food items (comma-separated numbers): ");
                String foodSelection = scanner.nextLine();
                String[] selectedFoodItems = foodSelection.split(",");
                for (String item : selectedFoodItems) {
                    int index = Integer.parseInt(item.trim()) - 1;
                    if (index >= 0 && index < foodMenu.size()) {
                        orderedFoods.add(foodMenu.get(index));
                        totalFoodCost += foodMenu.get(index).getPrice(); 
                    }
                }
                auditLog.log("Food ordered by " + guest.getName() + ": " + orderedFoods);
            }

            
            System.out.println("Do you want to provide feedback for your stay? (yes/no): ");
            String provideFeedback = scanner.nextLine();
            if ("yes".equalsIgnoreCase(provideFeedback)) {
                System.out.print("Enter your feedback comments: ");
                String comments = scanner.nextLine();
                System.out.print("Enter your rating (1 to 5): ");
                int rating = Integer.parseInt(scanner.nextLine());

                
                for (int roomId : selectedRoomIds) {
                    Feedback feedback = new Feedback(new Random().nextInt(1000), guest.getGuestId(), roomId, comments, rating);
                    System.out.println("Feedback for Room " + roomId + ": " + feedback);
                    auditLog.log("Feedback submitted by " + guest.getName() + " for Room " + roomId);
                }
            }

            double taxRate = 0.10; 
            double totalCostBeforeTax = totalRoomCost + totalFoodCost;
            double taxAmount = totalCostBeforeTax * taxRate;
            double totalBill = totalCostBeforeTax + taxAmount;

            
            System.out.println("\n--- Total Bill Breakdown ---");
            System.out.println("Room Cost: $" + totalRoomCost);
            System.out.println("Food Cost: $" + totalFoodCost);
            System.out.println("Tax (10%): $" + taxAmount);
            System.out.println("Total Bill: $" + totalBill);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
