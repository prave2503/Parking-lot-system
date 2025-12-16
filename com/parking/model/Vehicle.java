package com.parking.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String registrationNumber;
    private final VehicleType type;
    private final String color; // optional

    public Vehicle(String registrationNumber, VehicleType type, String color) {
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return type + " [" + registrationNumber + "] " + (color != null ? color : "");
    }
}

