package com.example.nepaltourismsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingFileHandler {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = DATA_DIR + "/bookings.json";

    public static void saveBookings(List<Booking> bookings) {
        createDataDirIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < bookings.size(); i++) {
                Booking b = bookings.get(i);
                writer.write(String.format(
                        "  {\"tourist\":\"%s\", \"guide\":\"%s\", \"attraction\":\"%s\", \"date\":\"%s\", \"endDate\":\"%s\", \"status\":\"%s\", \"cost\":%.2f}%s\n",
                        escape(b.getTourist().getName()),
                        escape(b.getGuide().getName()),
                        escape(b.getAttraction().getName()),
                        b.getDate(),
                        b.getEndDate(),
                        escape(b.getStatus()),
                        b.getCost(),
                        (i < bookings.size() - 1 ? "," : "")
                ));
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Booking> loadBookings() {
        createDataDirIfNeeded();
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                json.append(line.trim());

            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isBlank()) return bookings;

            String[] entries = content.split("\\},\\s*\\{");
            for (String entry : entries) {
                entry = entry.replace("{", "").replace("}", "");
                String[] fields = entry.split(",");

                String touristName = "", guideName = "", attractionName = "", status = "";
                LocalDate date = LocalDate.now();
                LocalDate endDate = LocalDate.now();
                double cost = 0.0;

                for (String field : fields) {
                    String[] pair = field.split(":", 2);
                    if (pair.length < 2) continue;

                    String key = pair[0].replace("\"", "").trim();
                    String value = pair[1].replace("\"", "").trim();

                    switch (key) {
                        case "tourist" -> touristName = value;
                        case "guide" -> guideName = value;
                        case "attraction" -> attractionName = value;
                        case "date" -> date = LocalDate.parse(value);
                        case "endDate" -> endDate = LocalDate.parse(value);
                        case "status" -> status = value;
                        case "cost" -> cost = Double.parseDouble(value);
                    }
                }

                try {
                    Tourist tourist = new Tourist(touristName, "Nepal", "999", "999", "23A");
                    Guide guide = new Guide(guideName, List.of("English"), 1, "N/A");
                    Attraction attraction = new Attraction(attractionName, "Cultural", "Kathmandu", "Description", 12.1);

                    bookings.add(new Booking(tourist, guide, attraction, date, endDate, status, cost));
                } catch (Exception e) {
                    System.err.println("Skipping invalid booking: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            // File does not exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    private static void createDataDirIfNeeded() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private static String escape(String input) {
        return input.replace("\"", "\\\"");
    }
}
