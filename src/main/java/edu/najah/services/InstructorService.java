package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class InstructorService {
    private static final Logger logger = Logger.getLogger(InstructorService.class.getName());
    private String Program_Title;
    private String Duration;
    private String Difficulty_Level;
    private Integer Price;
    private String name;
    private String password;

    private String role;


    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }


    public String getName() {
        return name;
    }

    public void setName(String nameValue) {
        name = nameValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordValue) {
        password = passwordValue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleValue) {
        role = roleValue;
    }


    // Getter and Setter methods
    public String getDuration() {
        return Duration;
    }

    public void setDuration(String durationValue) {
        Duration = durationValue;
    }

    public String getTitle() {
        return Program_Title;
    }

    public void setTitle(String programTitle) {
        Program_Title = programTitle;
    }

    public void setDifficaltyLevel(String difficultyLevelValue) {
        Difficulty_Level = difficultyLevelValue;
    }

    public String getDifficalty() {
        return Difficulty_Level;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(Integer priceValue) {
        Price = priceValue;
    }

    // Method to create a new program
    public Boolean announceProgram(Integer programID) {
        if (programID == null) {
            return false;
        }

        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return false;
            }

            // Retrieve the notifications list
            List<Map<String, Object>> notifications = (List<Map<String, Object>>) data.get("notifications");
            if (notifications == null) {
                notifications = new java.util.ArrayList<>();
                data.put("notifications", notifications);
            }

            // Create a notification entry for the program announcement
            Map<String, Object> notification = new java.util.HashMap<>();
            notification.put("program_id", programID);
            notification.put("message", "A new program has been announced! Program ID: " + programID);
            notification.put("timestamp", getCurrentDate());

            // Add the notification to the list
            notifications.add(notification);

            // Save the updated notifications back to the JSON file
            data.put("notifications", notifications);
            JsonFileHandler.saveJsonData(data);

            return true; // Return true if the announcement is successfully saved
        } catch (IOException e) {
            logger.severe("Error saving notification: " + programID + " - " + e.getMessage());
            return false; // Return false if an error occurs
        }
    }

    public String createProgram(String title, String duration, String difficalty, String price) {
        if (title.isEmpty()) {
            return "Title is empty";
        }

        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the programs list
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            if (programs == null) {
                programs = new java.util.ArrayList<>();
                data.put("programs", programs);
            }

            // Check if the program already exists
            for (Map<String, Object> program : programs) {
                if (program.get("title").equals(title)) {
                    return "Program already exists for this title";
                }
            }
            int lastId = 0;
            for (Map<String, Object> program : programs) {
                int currentId = Integer.parseInt(program.get("id").toString());
                if (currentId > lastId) {
                    lastId = currentId;
                }
            }
            Integer newId = lastId + 1;

            // Add the new program
            Map<String, Object> newProgram = new java.util.HashMap<>();
            newProgram.put("id", newId);
            newProgram.put("title", title);
            newProgram.put("duration", duration);
            newProgram.put("difficulty", difficalty);
            newProgram.put("price", price);


            programs.add(newProgram);

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);
            Boolean announceProgram = announceProgram(newId);
            if (announceProgram) {
                return "Program created successfully!";
            } else return "Error announce the program";
        } catch (IOException e) {
            logger.severe("Error saving program: " + title + " - " + e.getMessage());
            return "Error saving program: " + title + " - " + e.getMessage();
        }
    }

    // Method to delete a program
    public String deleteProgram(String title) {
        if (title == null || title.isEmpty()) {
            return "Title is empty";
        }

        Map<String, Object> data;
        try {
            // Load the existing data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "No data found";
            }

            List<Map<String, String>> programs = (List<Map<String, String>>) data.get("programs");

            if (programs == null || programs.isEmpty()) {
                return "Program does not exist"; // Return this if there are no programs at all
            }

            // Log the existing program titles to help debug
            logger.info("Existing programs in the system:");
            for (Map<String, String> program : programs) {
                String programTitle = program.get("title");
                logger.info("Program title: '" + programTitle + "'");
            }

            // Search for the program and remove it
            boolean found = false;
            for (Map<String, String> program : programs) {
                String programTitle = program.get("title");
                // Compare titles, trimming spaces and ensuring case insensitivity
                if (programTitle != null && programTitle.trim().equalsIgnoreCase(title.trim())) {
                    programs.remove(program);
                    found = true;
                    break;
                }
            }

            if (!found) {
                return "Program does not exist";
            }

            // Save the updated data back to the file
            JsonFileHandler.saveJsonData(data);

            return "Program deleted successfully";
        } catch (IOException e) {
            logger.severe("Error deleting program with title: " + title + " - " + e.getMessage());
            return "Error deleting program with title: " + title + " - " + e.getMessage();
        }
    }

    public String sendReport(String reportBody) {
        Map<String, Object> data;
        try {
            if (reportBody == null || reportBody.trim().isEmpty()) {
                return "Report is empty"; // Return error if the report body is null or empty
            }

            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "Report could not be sent"; // Return error if the data cannot be loaded
            }

            // Retrieve the message list
            List<Map<String, String>> messages = (List<Map<String, String>>) data.get("messages");
            if (messages == null) {
                messages = new java.util.ArrayList<>();
                data.put("messages", messages); // Fixing the key to match the "messages" key in the JSON file
            }

            // Create the report message with the provided messageBody
            Map<String, String> reportMessage = new java.util.HashMap<>();
            reportMessage.put("from", "instructor1");
            reportMessage.put("to", "client1");
            reportMessage.put("message_body", reportBody); // Use the passed message

            // Add the new message to the list
            messages.add(reportMessage);

            // Save the updated messages back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Report sent successfully"; // Return success if everything goes well
        } catch (IOException e) {
            logger.severe("Error sending report: " + e.getMessage());
            return "Report could not be sent"; // Return error if there's an exception during the process
        }
    }

    public String sendMessage(String messageBody) {
        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (messageBody == null || messageBody.trim().isEmpty()) {
                return "Message is empty";
            }

            // Retrieve the message list
            List<Map<String, String>> messages = (List<Map<String, String>>) data.get("messages");
            if (messages == null) {
                messages = new java.util.ArrayList<>();
                data.put("programs", messages);
            }

            // Simulate sending a message
            Map<String, String> message = new java.util.HashMap<>();
            message.put("from", "instructor1");
            message.put("to", "client1");
            message.put("message_body", "Hello, I noticed you're falling behind on your program. Let me know if you need help!");

            messages.add(message);

            // Save the updated messages back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Message sent successfully";
        } catch (IOException e) {
            logger.severe("Error sending message: " + e.getMessage());
            return "Error sending message: " + e.getMessage();
        }
    }

    public String sendFeedback(String feedbackBody) {
        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (feedbackBody == null || feedbackBody.trim().isEmpty()) {
                return "Feedback is empty"; // Return if feedback is empty
            }

            // Retrieve the feedback list
            List<Map<String, String>> feedbackList = (List<Map<String, String>>) data.get("feedback");
            if (feedbackList == null) {
                feedbackList = new ArrayList<>();
                data.put("feedback", feedbackList); // Initialize the feedback list if it's null
            }

            // Simulate sending feedback (similar to message structure)
            Map<String, String> feedback = new HashMap<>();
            feedback.put("from", "instructor1");
            feedback.put("to", "client1");
            feedback.put("feedback_body", feedbackBody);

            feedbackList.add(feedback);

            // Save the updated feedback list back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Feedback sent successfully"; // Return success message
        } catch (IOException e) {
            logger.severe("Error sending feedback: " + e.getMessage());
            return "Error sending feedback: " + e.getMessage(); // Return error message if failed
        }
    }

}
