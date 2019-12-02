package com.vaadin.example.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity("car")
public class Car extends Vehicle implements Serializable {
    private int numberOfDoors;
    private boolean hasSunRoof;

    public Car(String make, String plateNumber, Transmission transmission, BigDecimal rate, int numberOfDoors, boolean hasSunRoof) {
        super(make, plateNumber, transmission, rate);
        this.numberOfDoors = numberOfDoors;
        this.hasSunRoof = hasSunRoof;
    }

    public Car() {

    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public boolean isHasSunRoof() {
        return hasSunRoof;
    }

    @Override
    public String toString() {
        return "model.Car{" +
                "numberOfDoors=" + numberOfDoors +
                ", hasSunRoof=" + hasSunRoof +
                ", make=" + make +
                ", plateNumber='" + plateNumber + '\'' +
                ", transmission=" + transmission +
                ", rate=" + rate +
                '}';
    }
}
