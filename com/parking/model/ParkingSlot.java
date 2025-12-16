package com.parking.model;

import java.io.Serializable;

public class ParkingSlot implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int slotId;
    private final VehicleType allowedType;
    private boolean free;
    private String parkedVehicleReg; // registration number of parked vehicle (null if free)

    public ParkingSlot(int slotId, VehicleType allowedType) {
        this.slotId = slotId;
        this.allowedType = allowedType;
        this.free = true;
        this.parkedVehicleReg = null;
    }

    public int getSlotId() {
        return slotId;
    }

    public VehicleType getAllowedType() {
        return allowedType;
    }

    public boolean isFree() {
        return free;
    }

    public void occupy(String vehicleReg) {
        this.free = false;
        this.parkedVehicleReg = vehicleReg;
    }

    public void vacate() {
        this.free = true;
        this.parkedVehicleReg = null;
    }

    public String getParkedVehicleReg() {
        return parkedVehicleReg;
    }

    @Override
    public String toString() {
        return "Slot#" + slotId + " (" + allowedType + ") - " + (free ? "FREE" : "OCCUPIED by " + parkedVehicleReg);
    }
}
