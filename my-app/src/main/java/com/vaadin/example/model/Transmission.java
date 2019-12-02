package com.vaadin.example.model;

import java.util.Random;

public enum Transmission {
    MANUAL,
    AUTOMATIC;

    public static Transmission getRandomTransmission() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
