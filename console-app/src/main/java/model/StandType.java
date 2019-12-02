package model;

import java.util.Random;

public enum StandType {
    SINGLE,
    DOUBLE;

    public static StandType getRandomStandType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
