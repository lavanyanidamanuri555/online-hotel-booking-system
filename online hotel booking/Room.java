public class Room {
    private int roomId;
    private String roomType; // Single, Double, Suite
    private String roomStatus; // Available, Occupied, Reserved, In Progress, Under Maintenance
    private double price;
    private String bedType;

    public Room(int roomId, String roomType, String roomStatus, double price, String bedType) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.price = price;
        this.bedType = bedType;
    }

    public int getRoomId() {
        return roomId;
    }

    public synchronized void setRoomStatus(String status) {
        this.roomStatus = status;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public double getPrice() {
        return price;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getBedType() {
        return bedType;
    }
}
