package com.vaadin.example.model;

import java.util.Random;

public enum CarMake {
    AUDI,
    BMW,
    BENTLEY,
    BUGATTI,
    CHEVROLET,
    CORVETTE,
    FERRARI,
    HONDA,
    KIA,
    JEEP,
    LOTUS,
    LAMBORGHINI;

    public static CarMake getRandomMake() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
