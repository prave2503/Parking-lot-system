package com.parking.util;

import com.parking.model.VehicleType;

import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculator {
    // example rates (adjust as needed)
    private static final double CAR_RATE_PER_HOUR = 20.0;
    private static final double BIKE_RATE_PER_HOUR = 10.0;
    private static final double TRUCK_RATE_PER_HOUR = 40.0;
    private static final double MINIMUM_CHARGE = 10.0; // minimal fee

    public static double calculateFee(VehicleType type, LocalDateTime entry, LocalDateTime exit) {
        if (exit.isBefore(entry)) return 0.0;
        Duration dur = Duration.between(entry, exit);
        long minutes = dur.toMinutes();
        double hours = Math.ceil(minutes / 60.0); // charge by hour, ceil partial hours
        double rate = getRate(type);
        double fee = hours * rate;
        return Math.max(fee, MINIMUM_CHARGE);
    }

    private static double getRate(VehicleType type) {
        switch (type) {
            case CAR: return CAR_RATE_PER_HOUR;
            case BIKE: return BIKE_RATE_PER_HOUR;
            case TRUCK: return TRUCK_RATE_PER_HOUR;
            default: return CAR_RATE_PER_HOUR;
        }
    }
}
