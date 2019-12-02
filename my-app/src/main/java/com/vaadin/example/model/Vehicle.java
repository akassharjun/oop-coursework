package com.vaadin.example.model;

import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Vehicle  implements Serializable {
    @Id
    protected ObjectId id;
    protected String make;
    protected String plateNumber;
    protected Transmission transmission;
    protected BigDecimal rate;



    public Vehicle(String make, String plateNumber, Transmission transmission, BigDecimal rate) {
        this.make = make;
        this.plateNumber = plateNumber;
        this.transmission = transmission;
        this.rate = rate;
    }

    public Vehicle() {
    }

    public String getMake() {
        return make;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public BigDecimal getRate() {
        return rate;
    }

    private void setPlateNumber(String plateNumber) {
        // add validation for plate number
        this.plateNumber = plateNumber;
    }

    private void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }



    @Override
    public String toString() {
        return "model.Vehicle{" +
                "make=" + make +
                ", plateNumber='" + plateNumber + '\'' +
                ", transmission=" + transmission +
                ", rate=" + rate +
                '}';
    }
}
