package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class InstructorService {
    private static final Logger logger = Logger.getLogger(InstructorService.class.getName());

    private String programTitle;
    private String duration;
    private String difficultyLevel;
    private Integer price;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return programTitle;
    }

    public void setTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getDifficalty() {
        return difficultyLevel;
    }

    public void setDifficaltyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String createProgram(String title, String duration, String difficulty, String price) {
        if (title.trim().isEmpty())
            return "Title is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.getOrDefault("programs", new ArrayList<>());

            for (Map<String, Object> program : programs) {
                if (title.equals(program.get("title"))) return "Program already exists for this title";
            }

            int newId = programs.stream()
                    .mapToInt(p -> Integer.parseInt(p.get("id").toString()))
                    .max()
                    .orElse(0) + 1;

            Map<String, Object> newProgram = new HashMap<>();
            newProgram.put("id", newId);
            newProgram.put("title", title);
            newProgram.put("duration", duration);
            newProgram.put("difficulty", difficulty);
            newProgram.put("price", price);

            programs.add(newProgram);
            data.put("programs", programs);

            JsonFileHandler.saveJsonData(data);
            return "Program created successfully!";
        } catch (IOException e) {
            logger.severe("Error saving program: " + title + " - " + e.getMessage());
            return "Error saving program: " + title + " - " + e.getMessage();
        }
    }

    public String deleteProgram(String title) {
        if (title.trim().isEmpty())
            return "Title is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) return "No data found";

            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            if (programs.isEmpty())
                return "Program does not exist";

            boolean removed = programs.removeIf(program -> title.equalsIgnoreCase(((String) program.get("title")).trim()));
            if (!removed) return "Program does not exist";

            JsonFileHandler.saveJsonData(data);
            return "Program deleted successfully";
        } catch (IOException e) {
            logger.severe("Error deleting program with title: " + title + " - " + e.getMessage());
            return "Error deleting program with title: " + title + " - " + e.getMessage();
        }
    }

    public String sendReport(String reportBody) {
        return sendGenericMessage("messages", reportBody, "Report", "report_body");
    }

    public String sendMessage(String messageBody) {
        return sendGenericMessage("messages", messageBody, "Message", "message_body");
    }

    public String sendFeedback(String feedbackBody) {
        return sendGenericMessage("feedback", feedbackBody, "Feedback", "feedback_body");
    }

    private String sendGenericMessage(String key, String body, String type, String bodyKey) {
        if (body.trim().isEmpty()) //cover
            return type + " is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null)
                return type + " could not be sent";

            List<Map<String, String>> list = (List<Map<String, String>>) data.getOrDefault(key, new ArrayList<>());
            Map<String, String> message = buildMessage(body, bodyKey);

            list.add(message);
            data.put(key, list);

            JsonFileHandler.saveJsonData(data);
            return type + " sent successfully";
        } catch (IOException e) {
            logger.severe("Error sending " + type.toLowerCase() + ": " + e.getMessage());
            return type + " could not be sent";
        }
    }

    private Map<String, String> buildMessage(String body, String bodyKey) {
        Map<String, String> message = new HashMap<>();
        message.put("from", "instructor1");
        message.put("to", "client1");
        message.put(bodyKey, body);
        return message;
    }
}