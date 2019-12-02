package model;

import java.util.Random;

public enum Make {
    AUDI,
    BMW,
    BENTLEY,
    BUGATTI,
    DUCATTI,
    CHEVROLET,
    CORVETTE,
    FERRARI,
    HONDA,
    KIA,
    JEEP,
    LOTUS,
    LAMBORGHINI;

    public static Make getRandomMake() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}


