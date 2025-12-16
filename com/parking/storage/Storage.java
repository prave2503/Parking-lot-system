package com.parking.storage;

import com.parking.model.Ticket;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final String TICKET_FILE = "data/tickets.ser";

    public static void saveTickets(Map<String, Ticket> tickets) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TICKET_FILE))) {
            oos.writeObject(tickets);
        } catch (IOException e) {
            System.err.println("Failed saving tickets: " + e.getMessage());
        }
    }

    public static Map<String, Ticket> loadTickets() {
        File f = new File(TICKET_FILE);
        if (!f.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();

            // Safe casting (removes the warning)
            if (obj instanceof Map<?, ?>) {
                Map<?, ?> raw = (Map<?, ?>) obj;
                Map<String, Ticket> result = new HashMap<>();

                for (Map.Entry<?, ?> entry : raw.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();

                    if (key instanceof String && value instanceof Ticket) {
                        result.put((String) key, (Ticket) value);
                    }
                }

                return result;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed loading tickets: " + e.getMessage());
        }

        return new HashMap<>();
    }
}
