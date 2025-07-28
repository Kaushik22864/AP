package com.example.nepaltourismsystem;



import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuideFileHandler {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = DATA_DIR + "/guides.json";

    public static void saveGuides(List<Guide> guides) {
        createDataDirIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < guides.size(); i++) {
                Guide g = guides.get(i);
                String languages = String.join(",", g.getLanguages());
                writer.write(String.format(
                        "  {\"name\":\"%s\", \"languages\":\"%s\", \"experience\":%d, \"contact\":\"%s\"}%s\n",
                        escape(g.getName()), escape(languages),
                        g.getExperienceYears(), escape(g.getContact()),
                        (i < guides.size() - 1 ? "," : "")
                ));
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Guide> loadGuides() {
        createDataDirIfNeeded();
        List<Guide> guides = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                json.append(line.trim());

            String content = json.toString().replace("[", "").replace("]", "");
            if (content.isBlank()) return guides;

            String[] entries = content.split("\\},\\s*\\{");
            for (String entry : entries) {
                entry = entry.replace("{", "").replace("}", "");
                String[] fields = entry.split(",");

                String name = "", languagesStr = "", contact = "";
                int experience = 0;

                for (String field : fields) {
                    String[] pair = field.split(":", 2);
                    String key = pair[0].replace("\"", "").trim();
                    String value = pair[1].replace("\"", "").trim();

                    switch (key) {
                        case "name" -> name = value;
                        case "languages" -> languagesStr = value;
                        case "experience" -> experience = Integer.parseInt(value);
                        case "contact" -> contact = value;
                    }
                }

                try {
                    List<String> languages = Arrays.stream(languagesStr.split(","))
                            .map(String::trim)
                            .toList();
                    guides.add(new Guide(name, languages, experience, contact));
                } catch (EmptyFieldException | NullObjectException | NegativeValueException e) {
                    System.err.println("Skipping invalid guide entry: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            // No file yet
        } catch (IOException e) {
            e.printStackTrace();
        }

        return guides;
    }

    private static void createDataDirIfNeeded() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private static String escape(String input) {
        return input.replace("\"", "\\\"");
    }
}
