package com.parking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String ticketId;
    private final String vehicleReg;
    private final VehicleType vehicleType;
    private final int slotId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    public Ticket(String vehicleReg, VehicleType vehicleType, int slotId) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.slotId = slotId;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getSlotId() {
        return slotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void close(LocalDateTime exitTime, double fee) {
        this.exitTime = exitTime;
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + ticketId +
                ", reg='" + vehicleReg + '\'' +
                ", type=" + vehicleType +
                ", slot=" + slotId +
                ", entry=" + entryTime +
                (exitTime != null ? ", exit=" + exitTime + ", fee=" + fee : "") +
                '}';
    }
}
