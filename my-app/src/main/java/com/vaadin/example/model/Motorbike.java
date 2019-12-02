package com.vaadin.example.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity("motorbike")
public class Motorbike extends Vehicle  implements Serializable {
    private boolean hasPedals;
    private StandType standType;

    public Motorbike(String make, String plateNumber, Transmission transmission, BigDecimal rate, boolean hasPedals, StandType standType) {
        super(make, plateNumber, transmission, rate);
        this.hasPedals = hasPedals;
        this.standType = standType;
    }

    public Motorbike() {

    }

    public StandType getStandType() {
        return standType;
    }

    public boolean isHasPedals() {
        return hasPedals;
    }

    @Override
    public String toString() {
        return "model.Motorbike{" +
                "hasPedals=" + hasPedals +
                ", standType=" + standType +
                ", make=" + make +
                ", plateNumber='" + plateNumber + '\'' +
                ", transmission=" + transmission +
                ", rate=" + rate +
                '}';
    }
}
