package model;

import dev.morphia.annotations.Entity;

@Entity("booking")
public class Booking{
    private Schedule schedule;
    private Vehicle vehicle;

    public Booking(Schedule schedule, Vehicle vehicle) {
        this.schedule = schedule;
        this.vehicle = vehicle;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
