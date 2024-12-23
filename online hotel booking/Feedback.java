public class Feedback {
    private int feedbackId;
    private int guestId;
    private int roomId;
    private String comments;
    private int rating;

    // Constructor
    public Feedback(int feedbackId, int guestId, int roomId, String comments, int rating) {
        this.feedbackId = feedbackId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters
    public int getFeedbackId() {
        return feedbackId;
    }

    public int getGuestId() {
        return guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    // Setters (if needed)
    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Override toString for meaningful display
    @Override
    public String toString() {
        return "Feedback ID: " + feedbackId +
               ", Guest ID: " + guestId +
               ", Room ID: " + roomId +
               ", Rating: " + rating +
               ", Comments: '" + comments + "'";
    }
}
