package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClientService {
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    public String createUserProfile(String name, String dietaryPreference, String dietaryRestriction, int age, String fitnessGoal) {
        if (name.isEmpty()) {
            return "Name is empty";
        }

        try {
            Map<String, Object> data = loadData();
            Map<String, Map<String, String>> userProfiles = getUserProfiles(data);

            if (userProfiles.containsKey(name)) {
                return "Profile already exists for this name";
            }

            Map<String, String> profileDetails = createProfileDetails(name, dietaryPreference, dietaryRestriction, age, fitnessGoal);
            userProfiles.put(name, profileDetails);
            saveData(data);

            return "Profile created successfully";
        } catch (IOException e) {
            logger.severe("Error saving user profile for user: " + name + " - " + e.getMessage());
            return "Error saving user profile for user: " + name + " - " + e.getMessage();
        }
    }

    public List<Map<String, Object>> searchProgramsByFilter(String filterType) {
        List<Map<String, Object>> matchingPrograms = new ArrayList<>();

        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> programs = getPrograms(data);

            for (Map<String, Object> program : programs) {
                if (program.containsKey(filterType)) {
                    matchingPrograms.add(program);
                }
            }
        } catch (IOException | ClassCastException e) {
            logger.severe("Error reading the JSON file or processing data: " + e.getMessage());
        }

        return matchingPrograms;
    }

    public String enrollInProgram(String programName, String userName) {
        try {
            Map<String, Object> data = loadData();

            if (!isUserExists(data, userName)) {
                return "User doesn't exist";
            }

            if (isUserEnrolledInProgram(data, userName, programName)) {
                return "User is already enrolled in this program";
            }

            Integer programId = getProgramId(data, programName);
            if (programId == null) {
                return "The program doesn't exist";
            }
            if (programId == -1) {
                return "The program is completed";
            }

            enrollUserInProgram(data, userName, programName, programId);
            saveData(data);

            return "Enrolled successfully";
        } catch (IOException e) {
            logger.severe("Error reading or writing the JSON file: " + e.getMessage());
            return "Error reading or writing the JSON file: " + e.getMessage();
        }
    }

    public List<Map<String, Object>> getCompletedProgramsList(String userName) {
        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> programs = getPrograms(data);
            List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);

            if (programs == null || programs.isEmpty()) {
                logger.warning("No programs available");
                return new ArrayList<>();
            }

            if (enrolledUsers == null || enrolledUsers.isEmpty()) {
                logger.warning("No enrolled users available");
                return new ArrayList<>();
            }

            List<Map<String, Object>> completedPrograms = new ArrayList<>();

            for (Map<String, Object> program : programs) {
                for (Map<String, Object> enrolledUser : enrolledUsers) {
                    if (program.get("id").equals(enrolledUser.get("programId"))) {
                        if ("active".equals(program.get("status"))) {
                            logger.warning("Program is still active");
                            continue;
                        }
                        if (enrolledUser.get("userName").equals(userName)) {
                            completedPrograms.add(program);
                        }
                    }
                }
            }

            if (completedPrograms.isEmpty()) {
                logger.warning("User has no completed programs");
                return completedPrograms;
            }
            return completedPrograms;

        } catch (IOException e) {
            logger.severe("Error reading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public String reviewProgram(String programName, String userName, String rating, String review) {
        if (isInvalidRating(rating)) {
            return "Invalid rating. Please try again.";
        }

        if (isInvalidReview(review)) {
            return "Invalid review. Please try again.";
        }

        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> reviews = getReviews(data);
            List<Map<String, Object>> programs = getPrograms(data);

            int programId = findProgramId(programs, programName);
            if (programId == -1) {
                return "Program does not exist or is not completed.";
            }

            addReview(reviews, programId, userName, rating, review);
            saveData(data);

            return "Review submitted successfully";
        } catch (IOException | ClassCastException e) {
            logger.severe("Error reading or writing the JSON file: " + e.getMessage());
            return "Error reading or writing the JSON file: " + e.getMessage();
        }
    }

    private Map<String, Object> loadData() throws IOException {
        return JsonFileHandler.loadJsonData();
    }

    private void saveData(Map<String, Object> data) {
        try {
            JsonFileHandler.saveJsonData(data);
        } catch (IOException e) {
            logger.severe("Error saving data: " + e.getMessage());
        }
    }

    private Map<String, Map<String, String>> getUserProfiles(Map<String, Object> data) {
        return (Map<String, Map<String, String>>) data.getOrDefault("userProfiles", new HashMap<>());
    }

    private List<Map<String, Object>> getPrograms(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("programs", new ArrayList<>());
    }

    private List<Map<String, Object>> getEnrolledUsers(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("enrolledUsers", new ArrayList<>());
    }

    private List<Map<String, Object>> getReviews(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("reviews", new ArrayList<>());
    }

    private Map<String, String> createProfileDetails(String name, String dietaryPreference, String dietaryRestriction, int age, String fitnessGoal) {
        Map<String, String> profileDetails = new HashMap<>();
        profileDetails.put("name", name);
        profileDetails.put("dietaryPreference", dietaryPreference);
        profileDetails.put("dietaryRestriction", dietaryRestriction);
        profileDetails.put("age", String.valueOf(age));
        profileDetails.put("fitnessGoal", fitnessGoal);
        return profileDetails;
    }

    private boolean isUserExists(Map<String, Object> data, String userName) {
        Map<String, Object> users = (Map<String, Object>) data.get("users");
        return users.containsKey(userName);
    }

    private boolean isUserEnrolledInProgram(Map<String, Object> data, String userName, String programName) {
        List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);
        for (Map<String, Object> enrollment : enrolledUsers) {
            if (userName.equals(enrollment.get("userName")) && programName.equals(enrollment.get("programName"))) {
                return true;
            }
        }
        return false;
    }

    private Integer getProgramId(Map<String, Object> data, String programName) {
        List<Map<String, Object>> programs = getPrograms(data);
        for (Map<String, Object> program : programs) {
            if (programName.equals(program.get("title")) && "active".equals(program.get("status"))) {
                return (Integer) program.get("id");
            }
            if (programName.equals(program.get("title")) && "completed".equals(program.get("status"))) {
                return -1;
            }
        }
        return null;
    }

    private void enrollUserInProgram(Map<String, Object> data, String userName, String programName, Integer programId) {
        List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);
        Map<String, Object> userEnrollment = new HashMap<>();
        userEnrollment.put("programId", programId);
        userEnrollment.put("programName", programName);
        userEnrollment.put("userName", userName);
        enrolledUsers.add(userEnrollment);
    }


    private boolean isInvalidRating(String rating) {
        int rate = Integer.parseInt(rating);
        return rate <= 0 || rate > 10;
    }

    private boolean isInvalidReview(String review) {
        return review == null || review.trim().isEmpty();
    }

    private int findProgramId(List<Map<String, Object>> programs, String programName) {
        for (Map<String, Object> program : programs) {
            if (programName.equals(program.get("title")) && "completed".equals(program.get("status"))) {
                return (Integer) program.get("id");
            }
        }
        return -1;
    }

    private void addReview(List<Map<String, Object>> reviews, int programId, String userName, String rating, String review) {
        Map<String, Object> newReview = new HashMap<>();
        newReview.put("programId", programId);
        newReview.put("userName", userName);
        newReview.put("rating", Integer.parseInt(rating));
        newReview.put("review", review);
        reviews.add(newReview);
    }
}
