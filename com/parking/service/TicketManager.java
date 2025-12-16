package com.parking.service;

import com.parking.model.Ticket;
import com.parking.storage.Storage;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TicketManager {
    private final Map<String, Ticket> tickets;

    public TicketManager() {
        Map<String, Ticket> loaded = Storage.loadTickets();
        tickets = new ConcurrentHashMap<>(loaded);
    }

    public void saveAll() {
        Storage.saveTickets(tickets);
    }

    public void addTicket(Ticket ticket) {
        tickets.put(ticket.getTicketId(), ticket);
        saveAll();
    }

    public Optional<Ticket> getTicketByVehicleReg(String reg) {
        return tickets.values().stream()
                .filter(t -> t.getVehicleReg().equalsIgnoreCase(reg) && t.getExitTime() == null)
                .findFirst();
    }

    public Optional<Ticket> getTicketById(String id) {
        Ticket t = tickets.get(id);
        if (t == null) return Optional.empty();
        return Optional.of(t);
    }

    public void closeTicket(Ticket ticket, LocalDateTime exitTime, double fee) {
        ticket.close(exitTime, fee);
        tickets.put(ticket.getTicketId(), ticket);
        saveAll();
    }

    public Map<String, Ticket> allTickets() {
        return tickets;
    }
}
