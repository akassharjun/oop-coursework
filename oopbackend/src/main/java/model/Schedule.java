package model;

import model.Date;

public class Schedule {
    private Date pickUpDate;
    private Date dropOffDate;

    public Schedule(Date pickUpDate, Date dropOffDate) {
        this.setPickUpDate(pickUpDate, dropOffDate);
        this.dropOffDate = dropOffDate;
    }

    public Schedule() {
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    private void setPickUpDate(Date pickUpDate, Date dropOffDate) {
        // validate dates
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        // validate dates
        this.dropOffDate = dropOffDate;
    }
}
