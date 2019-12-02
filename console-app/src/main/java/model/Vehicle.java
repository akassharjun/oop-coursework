package model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity("vehicles")
public abstract class Vehicle  implements Serializable {
    @Id
    private ObjectId id;
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

    public ObjectId getId() {
        return id;
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
        return "Vehicle{" +
                "make=" + make +
                ", plateNumber='" + plateNumber + '\'' +
                ", transmission=" + transmission +
                ", rate=" + rate +
                '}';
    }
}
