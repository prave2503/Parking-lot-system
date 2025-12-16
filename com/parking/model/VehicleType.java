package com.parking.model;

public enum VehicleType {
    CAR, BIKE, TRUCK;

    public static VehicleType fromString(String s) {
        switch (s.trim().toUpperCase()) {
            case "CAR": return CAR;
            case "BIKE": return BIKE;
            case "TRUCK": return TRUCK;
            default: throw new IllegalArgumentException("Unknown vehicle type: " + s);
        }
    }
}
