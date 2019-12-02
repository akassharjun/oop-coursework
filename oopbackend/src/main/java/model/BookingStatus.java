package model;

public class BookingStatus {
    boolean success;

    public BookingStatus(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
