package model;

import java.util.Random;

public enum BikeMake {
    DUCATTI,
    BAJAJ,
    HONDA,
    YAMAHA,
    SCOOTYPEP,
    KAWASAKI;


    public static BikeMake getRandomMake() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

