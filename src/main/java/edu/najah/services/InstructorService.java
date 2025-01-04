package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service class to manage instructor-related operations such as creating, deleting programs,
 * and sending reports, messages, or feedback.
 */
public class InstructorService {
    public static final String PROGRAMS = "programs";
    public static final String TITLE = "title";
    private static final Logger logger = Logger.getLogger(InstructorService.class.getName());
    private String programTitle;
    private String duration;
    private String difficultyLevel;
    private Integer price;

    /**
     * Gets the duration of the program.
     *
     * @return the duration of the program
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the program.
     *
     * @param duration the duration of the program
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the title of the program.
     *
     * @return the title of the program
     */
    public String getTitle() {
        return programTitle;
    }

    /**
     * Sets the title of the program.
     *
     * @param programTitle the title of the program
     */
    public void setTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    /**
     * Gets the difficulty level of the program.
     *
     * @return the difficulty level of the program
     */
    public String getDifficalty() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level of the program.
     *
     * @param difficultyLevel the difficulty level of the program
     */
    public void setDifficaltyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Gets the price of the program.
     *
     * @return the price of the program
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the price of the program.
     *
     * @param price the price of the program
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Creates a new program with the specified details.
     *
     * @param title      the title of the program
     * @param duration   the duration of the program
     * @param difficulty the difficulty level of the program
     * @param price      the price of the program
     * @return a message indicating the success or failure of the operation
     */
    public String createProgram(String title, String duration, String difficulty, String price) {
        if (title.trim().isEmpty())
            return "Title is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.getOrDefault(PROGRAMS, new ArrayList<>());

            for (Map<String, Object> program : programs) {
                if (title.equals(program.get(TITLE))) return "Program already exists for this title";
            }

            int newId = programs.stream()
                    .mapToInt(p -> Integer.parseInt(p.get("id").toString()))
                    .max()
                    .orElse(0) + 1;

            Map<String, Object> newProgram = new HashMap<>();
            newProgram.put("id", newId);
            newProgram.put(TITLE, title);
            newProgram.put("duration", duration);
            newProgram.put("difficulty", difficulty);
            newProgram.put("price", price);

            programs.add(newProgram);
            data.put(PROGRAMS, programs);

            JsonFileHandler.saveJsonData(data);
            return "Program created successfully!";
        } catch (IOException e) {
            logger.severe("Error saving program: " + title + " - " + e.getMessage());
            return "Error saving program: " + title + " - " + e.getMessage();
        }
    }

    /**
     * Deletes a program by its title.
     *
     * @param title the title of the program to delete
     * @return a message indicating the success or failure of the operation
     */
    public String deleteProgram(String title) {
        if (title.trim().isEmpty())
            return "Title is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) return "No data found";

            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get(PROGRAMS);
            if (programs.isEmpty())
                return "Program does not exist";

            boolean removed = programs.removeIf(program -> title.equalsIgnoreCase(((String) program.get(TITLE)).trim()));
            if (!removed) return "Program does not exist";

            JsonFileHandler.saveJsonData(data);
            return "Program deleted successfully";
        } catch (IOException e) {
            logger.severe("Error deleting program with title: " + title + " - " + e.getMessage());
            return "Error deleting program with title: " + title + " - " + e.getMessage();
        }
    }

    /**
     * Sends a report with the specified content.
     *
     * @param reportBody the content of the report
     * @return a message indicating the success or failure of the operation
     */
    public String sendReport(String reportBody, String reciver, String sender) {
        return sendGenericMessage("messages", reportBody, "Report", "report_body", reciver, sender);
    }

    /**
     * Sends a message with the specified content.
     *
     * @param messageBody the content of the message
     * @return a message indicating the success or failure of the operation
     */
    public String sendMessage(String messageBody, String reciver, String sender) {
        return sendGenericMessage("messages", messageBody, "Message", "message_body", reciver, sender);
    }

    /**
     * Sends feedback with the specified content.
     *
     * @param feedbackBody the content of the feedback
     * @return a message indicating the success or failure of the operation
     */
    public String sendFeedback(String feedbackBody, String reciver, String sender) {
        return sendGenericMessage("feedback", feedbackBody, "Feedback", "feedback_body", reciver, sender);
    }

    /**
     * Sends a generic message.
     *
     * @param key     the key identifying the message type
     * @param body    the content of the message
     * @param type    the type of message (e.g., Report, Message, Feedback)
     * @param bodyKey the key used for the message content in the data map
     * @return a message indicating the success or failure of the operation
     */
    private String sendGenericMessage(String key, String body, String type, String bodyKey, String reciver, String sender) {
        if (body.trim().isEmpty())
            return type + " is empty";

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null)
                return type + " could not be sent";

            List<Map<String, String>> list = (List<Map<String, String>>) data.getOrDefault(key, new ArrayList<>());
            Map<String, String> message = buildMessage(body, bodyKey, reciver, sender);

            list.add(message);
            data.put(key, list);

            JsonFileHandler.saveJsonData(data);
            return type + " sent successfully";
        } catch (IOException e) {
            logger.severe("Error sending " + type.toLowerCase() + ": " + e.getMessage());
            return type + " could not be sent";
        }
    }

    /**
     * Builds a generic message with the specified content.
     *
     * @param body    the content of the message
     * @param bodyKey the key used for the message content in the map
     * @return a map containing the message details
     */
    private Map<String, String> buildMessage(String body, String bodyKey, String reciver, String sender) {
        Map<String, String> message = new HashMap<>();
        message.put("from", sender);
        message.put("to", reciver);
        message.put(bodyKey, body);
        return message;
    }
}
