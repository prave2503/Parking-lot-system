package com.parking;

import com.parking.db.DBConnection;
import com.parking.db.TicketDAO;
import com.parking.model.Ticket;
import com.parking.model.VehicleType;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String
        [] args) {

        // 1️⃣ Test DB Connection
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("❌ Database connection failed");
            return;
        }
        System.out.println("✅ Database connected");

        Scanner sc = new Scanner(System.in);

        System.out.println("===== PARKING LOT SYSTEM =====");

        // 2️⃣ Take input
        System.out.print("Enter vehicle registration number: ");
        String reg = sc.nextLine();

        System.out.print("Enter vehicle type (CAR / BIKE): ");
        VehicleType type = VehicleType.valueOf(sc.nextLine().toUpperCase());

        System.out.print("Enter slot number: ");
        int slotId = sc.nextInt();

        // 3️⃣ Create ticket
        Ticket ticket = new Ticket(reg, type, slotId);

        // 4️⃣ Save ticket to DB
        TicketDAO.saveTicket(ticket);
        System.out.println("✅ Vehicle parked successfully");
        System.out.println("Ticket ID: " + ticket.getTicketId());

        // 5️⃣ Unparking simulation
        System.out.println("\nPress ENTER to unpark vehicle...");
        sc.nextLine(); // consume newline
        sc.nextLine();

        double fee = 50.0; // example fee
        ticket.close(LocalDateTime.now(), fee);

        TicketDAO.updateExit(ticket);
        System.out.println("✅ Vehicle unparked");
        System.out.println("Fee: ₹" + ticket.getFee());

        sc.close();
    }
}
