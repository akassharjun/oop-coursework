package model;

import dev.morphia.annotations.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity("vehicles")
public class Car extends Vehicle implements Serializable {
    private int numberOfDoors;
    private boolean hasSunRoof;

    public Car(Make make, String plateNumber, Transmission transmission, BigDecimal rate, int numberOfDoors, boolean hasSunRoof) {
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
