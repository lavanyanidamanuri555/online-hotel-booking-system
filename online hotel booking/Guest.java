import java.util.*;

public class Guest {
    private int guestId;
    private String name;
    private String contactInfo;
    private String passportOrNationalId;
    private String email;
    private List<Reservation> reservations;  

    public Guest(int guestId, String name, String contactInfo, String passportOrNationalId, String email) {
        this.guestId = guestId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.passportOrNationalId = passportOrNationalId;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    public int getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    @Override
    public String toString() {
        return "Guest ID: " + guestId + ", Name: " + name;
    }
}
