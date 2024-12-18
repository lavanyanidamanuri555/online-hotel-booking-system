public class Invoice {
    private int invoiceId;
    private int reservationId;
    private double taxAmount;
    private double localTaxRate;
    private double serviceFee;

    private static final double DAILY_TAX_RATE = 10.0;

    public Invoice(int invoiceId, int reservationId, double localTaxRate, double serviceFee) {
        this.invoiceId = invoiceId;
        this.reservationId = reservationId;
        this.localTaxRate = localTaxRate;
        this.serviceFee = serviceFee;
    }

    public double generateInvoice(Reservation reservation, Room room) {
        long diff = reservation.getCheckOut().getTime() - reservation.getCheckIn().getTime();
        long daysStayed = diff / (1000 * 60 * 60 * 24);

        double roomCharge = daysStayed * room.getPrice();
        this.taxAmount = calculateDailyTax(daysStayed) + calculateLocalTax(roomCharge);

        return roomCharge + this.taxAmount + this.serviceFee;
    }

    private double calculateDailyTax(long daysStayed) {
        return DAILY_TAX_RATE * daysStayed;
    }

    private double calculateLocalTax(double roomCharge) {
        return (roomCharge * localTaxRate) / 100;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getServiceFee() {
        return serviceFee;
    }
}
