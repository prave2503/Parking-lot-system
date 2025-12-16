package com.parking.db;

import com.parking.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAO {

    // INSERT ticket (vehicle parked)
    public static void saveTicket(Ticket ticket) {

        String sql = "INSERT INTO tickets (ticket_id, vehicle_number, time_in) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ticket.getTicketId());
            ps.setString(2, ticket.getVehicleReg());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(ticket.getEntryTime()));

            ps.executeUpdate();
            System.out.println("🗄️ Ticket saved in database");

        } catch (Exception e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }

    // UPDATE ticket (vehicle unparked)
    public static void updateExit(Ticket ticket) {

        String sql = "UPDATE tickets SET time_out=?, amount=? WHERE ticket_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(ticket.getExitTime()));
            ps.setDouble(2, ticket.getFee());
            ps.setString(3, ticket.getTicketId());

            ps.executeUpdate();
            System.out.println("🗄️ Ticket updated in database");

        } catch (Exception e) {
            System.out.println("Error updating ticket: " + e.getMessage());
        }
    }
}
