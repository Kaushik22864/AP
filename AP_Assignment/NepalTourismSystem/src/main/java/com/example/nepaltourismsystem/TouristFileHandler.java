package com.example.nepaltourismsystem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TouristFileHandler {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = DATA_DIR + "/tourists.json";

    public static void saveTourists(List<Tourist> tourists) {
        createDataDirIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < tourists.size(); i++) {
                Tourist t = tourists.get(i);
                writer.write(String.format(
                        "  {\"name\":\"%s\", \"nationality\":\"%s\", \"contact\":\"%s\", \"emergencyContact\":\"%s\",\"passportNumber\"}%s\n",
                        escape(t.getName()), escape(t.getNationality()),
                        escape(t.getContact()), escape(t.getEmergencyContact()), escape(t.getPassportNumber()),
                        (i < tourists.size() - 1 ? "," : "")
                ));
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Tourist> loadTourists() {
        createDataDirIfNeeded();
        List<Tourist> tourists = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                json.append(line.trim());

            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isBlank()) return tourists;

            String[] entries = content.split("\\},\\s*\\{");
            for (String entry : entries) {
                entry = entry.replace("{", "").replace("}", "");
                String[] fields = entry.split(",");

                String name = "", nationality = "", contact = "", emergencyContact = "", passportNumber = "";

                for (String field : fields) {
                    String[] pair = field.split(":", 2);
                    String key = pair[0].replace("\"", "").trim();
                    String value = pair[1].replace("\"", "").trim();

                    switch (key) {
                        case "name" -> name = value;
                        case "nationality" -> nationality = value;
                        case "contact" -> contact = value;
                        case "emergencyContact" -> emergencyContact = value;
                        case "passportNumber" -> passportNumber = value;
                    }
                }

                try {
                    tourists.add(new Tourist(name, nationality, contact, emergencyContact, passportNumber));
                } catch (EmptyFieldException e) {
                    System.err.println("Skipping invalid tourist entry: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            // No file yet
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tourists;
    }

    private static void createDataDirIfNeeded() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private static String escape(String input) {
        return input.replace("\"", "\\\"");
    }
}
