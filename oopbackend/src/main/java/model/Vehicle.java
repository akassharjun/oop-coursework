package model;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Vehicle  implements Serializable {
    protected Make make;
    protected String plateNumber;
    protected Transmission transmission;
    protected BigDecimal rate;

    public Vehicle() {
    }

    public Vehicle(Make make, String plateNumber, Transmission transmission, BigDecimal rate) {
        this.make = make;
        this.plateNumber = plateNumber;
        this.transmission = transmission;
        this.rate = rate;
    }

    public Make getMake() {
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
        return "model.model.Vehicle{" +
                "make=" + make +
                ", plateNumber='" + plateNumber + '\'' +
                ", transmission=" + transmission +
                ", rate=" + rate +
                '}';
    }
}
