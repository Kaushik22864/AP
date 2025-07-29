package com.example.nepaltourismsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionFileHandler {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = DATA_DIR + "/attractions.json";

    public static void saveAttractions(List<Attraction> attractions) {
        createDataDirIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < attractions.size(); i++) {
                Attraction a = attractions.get(i);
                writer.write(String.format(
                        "  {\"name\":\"%s\", \"type\":\"%s\", \"location\":\"%s\", \"description\":\"%s\", \"altitude\":%.2f}%s\n",
                        escape(a.getName()), escape(a.getType()),
                        escape(a.getLocation()), escape(a.getDescription()),
                        a.getAltitude(),
                        (i < attractions.size() - 1 ? "," : "")
                ));
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Attraction> loadAttractions() {
        createDataDirIfNeeded();
        List<Attraction> attractions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                json.append(line.trim());

            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isBlank()) return attractions;

            String[] entries = content.split("\\},\\s*\\{");
            for (String entry : entries) {
                entry = entry.trim();
                if (!entry.startsWith("{")) entry = "{" + entry;
                if (!entry.endsWith("}")) entry = entry + "}";

                String[] fields = entry.replace("{", "").replace("}", "").split(",");

                String name = "", type = "", location = "", description = "";
                double altitude = 0.0;

                for (String field : fields) {
                    String[] pair = field.split(":", 2);
                    if (pair.length < 2) continue;

                    String key = pair[0].replace("\"", "").trim();
                    String value = pair[1].replace("\"", "").trim();

                    switch (key) {
                        case "name" -> name = value;
                        case "type" -> type = value;
                        case "location" -> location = value;
                        case "description" -> description = value;
                        case "altitude" -> {
                            try {
                                altitude = Double.parseDouble(value);
                            } catch (NumberFormatException ignored) {
                                altitude = 0.0;
                            }
                        }
                    }
                }

                try {
                    attractions.add(new Attraction(name, type, location, description, altitude));
                } catch (EmptyFieldException e) {
                    System.err.println("Skipping invalid attraction: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            // No file yet
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    private static void createDataDirIfNeeded() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private static String escape(String input) {
        return input.replace("\"", "\\\"");
    }
}
