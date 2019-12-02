package model;

import dev.morphia.annotations.Entity;

@Entity("booking")
public class Booking{
    private Schedule schedule;
    private String plateNumber;


    public Booking(Schedule schedule, String plateNumber) {
        this.schedule = schedule;
        this.plateNumber = plateNumber;
    }

    public Booking() {

    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Schedule getSchedule() {
        return schedule;
    }

}
