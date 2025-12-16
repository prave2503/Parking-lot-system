package com.parking.service;

import com.parking.model.ParkingSlot;
import com.parking.model.Ticket;
import com.parking.model.Vehicle;
import com.parking.model.VehicleType;
import com.parking.util.FeeCalculator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final TicketManager ticketManager;

    public ParkingLot(int numCarSlots, int numBikeSlots, int numTruckSlots, TicketManager ticketManager) {
        this.ticketManager = ticketManager;
        int id = 1;
        for (int i = 0; i < numCarSlots; i++) slots.add(new ParkingSlot(id++, VehicleType.CAR));
        for (int i = 0; i < numBikeSlots; i++) slots.add(new ParkingSlot(id++, VehicleType.BIKE));
        for (int i = 0; i < numTruckSlots; i++) slots.add(new ParkingSlot(id++, VehicleType.TRUCK));
    }

    public Optional<ParkingSlot> findFreeSlotFor(VehicleType type) {
        return slots.stream()
                .filter(ParkingSlot::isFree)
                .filter(s -> s.getAllowedType() == type)
                .findFirst();
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        Optional<ParkingSlot> opt = findFreeSlotFor(vehicle.getType());
        if (!opt.isPresent()) {
            throw new IllegalStateException("No free slot for vehicle type: " + vehicle.getType());
        }
        ParkingSlot slot = opt.get();
        slot.occupy(vehicle.getRegistrationNumber());
        Ticket ticket = new Ticket(vehicle.getRegistrationNumber(), vehicle.getType(), slot.getSlotId());
        ticketManager.addTicket(ticket);
        return ticket;
    }

    public double leaveVehicle(String registrationNumber) {
        Optional<ParkingSlot> optSlot = slots.stream()
                .filter(s -> !s.isFree() && registrationNumber.equalsIgnoreCase(s.getParkedVehicleReg()))
                .findFirst();

        if (!optSlot.isPresent()) {
            throw new NoSuchElementException("Vehicle with registration " + registrationNumber + " not found in any slot.");
        }

        ParkingSlot slot = optSlot.get();
        Optional<Ticket> ot = ticketManager.getTicketByVehicleReg(registrationNumber);
        if (!ot.isPresent()) {
            throw new IllegalStateException("Ticket not found for vehicle " + registrationNumber);
        }
        Ticket ticket = ot.get();
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = FeeCalculator.calculateFee(ticket.getVehicleType(), ticket.getEntryTime(), exitTime);
        ticketManager.closeTicket(ticket, exitTime, fee);
        slot.vacate();
        return fee;
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public List<ParkingSlot> freeSlotsByType(VehicleType type) {
        return slots.stream()
                .filter(ParkingSlot::isFree)
                .filter(s -> s.getAllowedType() == type)
                .collect(Collectors.toList());
    }
}

