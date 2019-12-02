package com.vaadin.example.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("booking")
public class Booking {
    @Id
    private ObjectId id;
    private Schedule schedule;
    private Vehicle vehicle;

    public Booking(Schedule schedule, Vehicle vehicle) {
        this.schedule = schedule;
        this.vehicle = vehicle;
    }

    public Booking() {

    }


    public Schedule getSchedule() {
        return schedule;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
