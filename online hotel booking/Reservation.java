import java.util.Date;
import java.util.List;

public class Reservation {
    private int reservationId;
    private List<Integer> roomIds; 
    private int guestId;
    private Date checkIn;
    private Date checkOut;
    private String paymentStatus;

    public Reservation(int reservationId, List<Integer> roomIds, int guestId, Date checkIn, Date checkOut, String paymentStatus) {
        this.reservationId = reservationId;
        this.roomIds = roomIds;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paymentStatus = paymentStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public List<Integer> getRoomIds() {
        return roomIds;
    }

    public int getGuestId() {
        return guestId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Rooms: " + roomIds + ", Guest ID: " + guestId + ", Check-In: " + checkIn + ", Check-Out: " + checkOut + ", Payment Status: " + paymentStatus;
    }
}
