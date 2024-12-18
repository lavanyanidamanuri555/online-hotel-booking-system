public class Guest {
    private int guestId;
    private String name;
    private String contactInfo;
    private String passportOrNationalId;
    private String email;

    public Guest(int guestId, String name, String contactInfo, String passportOrNationalId, String email) {
        this.guestId = guestId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.passportOrNationalId = passportOrNationalId;
        this.email = email;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getPassportOrNationalId() {
        return passportOrNationalId;
    }

    public String getEmail() {
        return email;
    }
}
