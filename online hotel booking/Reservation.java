import java.util.Date;

public class Reservation {
    private int reservationId;
    private int roomId;
    private int guestId;
    private Date checkIn;
    private Date checkOut;
    private String paymentStatus;

    public Reservation(int reservationId, int roomId, int guestId, Date checkIn, Date checkOut, String paymentStatus) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paymentStatus = paymentStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getRoomId() {
        return roomId;
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
}
