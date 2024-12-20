package edu.najah.Services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private String clientName;
    private String clientPreferenceValue;
    private String clientRestrictionValue;
    private String fitnessGoalValue;
    private int ageValue;

    public String getName() {
        return clientName;
    }

    public void setName(String name) {
        clientName = name;
    }

    public String getDietaryPreference() {
        return clientPreferenceValue;
    }

    public void setDietaryPreference(String preferenceValue) {
        clientPreferenceValue = preferenceValue;
    }

    public String getDietaryRestrictions() {
        return clientRestrictionValue;
    }

    public void setDietaryRestrictions(String restrictionValue) {
        clientRestrictionValue = restrictionValue;
    }

    public String getFitnessGoal() {
        return new String(fitnessGoalValue);
    }

    public void setFitnessGoal(String fitnessGoal) {
        fitnessGoalValue = fitnessGoal;
    }

    public int getAge() {
        return Integer.valueOf(ageValue);
    }

    public void setAge(int age) {
        ageValue = age;
    }

    //returns Profile created successfully only if the profile is created successfully
    public String createUserProfile(String name, String dietaryPreference, String dietaryRestriction, int age, String fitnessGoal) {
        if (name.isEmpty()) {
            return "Name is empty";
        }

        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            Map<String, Map<String, String>> userProfiles = (Map<String, Map<String, String>>) data.get("userProfiles");
            if (userProfiles == null) {
                userProfiles = new java.util.HashMap<>();
                data.put("userProfiles", userProfiles);
            }

            if (userProfiles.containsKey(name)) {
                return "Profile already exists for this name";
            }

            // Create the user profile details
            Map<String, String> profileDetails = new java.util.HashMap<>();
            profileDetails.put("name", name);
            profileDetails.put("dietaryPreference", dietaryPreference);
            profileDetails.put("dietaryRestriction", dietaryRestriction);
            profileDetails.put("age", String.valueOf(age));  // Convert age to string
            profileDetails.put("fitnessGoal", fitnessGoal);

            userProfiles.put(name, profileDetails);

            JsonFileHandler.saveJsonData(data);
            return "Profile created successfully";
        } catch (IOException e) {
            logger.severe("Error saving user profile for user: " + name + " - " + e.getMessage());
            return "Error saving user profile for user: " + name + " - " + e.getMessage();
        }
    }

    public List<Map<String, Object>> searchProgramsByFilter(String filterType) {
        List<Map<String, Object>> matchingPrograms = new ArrayList<>();
        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (data == null || !data.containsKey("programs")) {
                logger.warning("No programs found in the JSON file.");
                return matchingPrograms;
            }
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            for (Map<String, Object> program : programs) {
                if (program.containsKey(filterType)) {
                    matchingPrograms.add(program);
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading the JSON file: " + e.getMessage());
        } catch (ClassCastException e) {
            logger.severe("Error processing the programs data: " + e.getMessage());
        }

        return matchingPrograms;
    }

    public String enrollInProgram(String programName, String userName) {
        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();

            // Retrieve data from JSON
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            Map<String, Object> users = (Map<String, Object>) data.get("users");
            List<Map<String, Object>> enrolledUsers = (List<Map<String, Object>>) data.get("enrolledUsers");

            if (enrolledUsers == null) {
                enrolledUsers = new ArrayList<>();
                data.put("enrolledUsers", enrolledUsers);
            }

            if (!users.containsKey(userName)) {
                logger.warning("User doesn't exist.");
                return "User doesn't exist";
            }

            for (Map<String, Object> enrollment : enrolledUsers) {
                if (enrollment != null && userName.equals(enrollment.get("userName"))) {
                    logger.warning("User is already enrolled.");
                    return "User is already enrolled in this program";
                }
            }

            Integer programId = null;
            for (Map<String, Object> program : programs) {
                if (programName.equals(program.get("title"))) {
                    if (!"active".equals(program.get("status"))) {
                        logger.warning("The program is completed.");
                        return "The program is completed";
                    }
                    programId = (Integer) program.get("id");
                    break;
                }
            }

            if (programId == null) {
                logger.warning("Program does not exist.");
                return "The program doesn't exist";
            }

            Map<String, Object> userEnrollment = new HashMap<>();
            userEnrollment.put("programId", programId);
            userEnrollment.put("userName", userName);
            enrolledUsers.add(userEnrollment);

            data.put("enrolledUsers", enrolledUsers);
            JsonFileHandler.saveJsonData(data);

            logger.info("User " + userName + " successfully enrolled in " + programName);
            return "Enrolled successfully";

        } catch (IOException e) {
            logger.severe("Error reading or writing the JSON file: " + e.getMessage());
            return "Error reading or writing the JSON file: " + e.getMessage();
        }
    }


    public String generateCompletedProgramsList(String programName) {
        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();

            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            List<Map<String, Object>> enrolledUsers = (List<Map<String, Object>>) data.get("enrolledUsers");

            if (programs == null || programs.isEmpty()) {
                return "No programs available";
            }

            if (enrolledUsers == null || enrolledUsers.isEmpty()) {
                return "No enrolled users found";
            }

            List<Map<String, Object>> completedPrograms = new ArrayList<>();

            boolean programFound = false;
            for (Map<String, Object> program : programs) {
                if (programName.equals(program.get("title"))) {
                    programFound = true;
                    if ("completed".equals(program.get("status"))) {
                        boolean userEnrolled = false;
                        for (Map<String, Object> enrollment : enrolledUsers) {
                            if (enrollment.get("programId").equals(program.get("id")) && getName().equals(enrollment.get("userName"))) {
                                userEnrolled = true;
                                completedPrograms.add(program);
                                break;
                            }
                        }
                        if (!userEnrolled) {
                            return "User isn't enrolled in this program";
                        }
                    } else {
                        return "program is still active";
                    }
                }
            }

            if (!programFound) {
                return "No completed programs match the specified name";
            }

            if (completedPrograms.isEmpty()) {
                return "There is no completed programs";
            }

            StringBuilder result = new StringBuilder("Completed Programs:\n");
            for (Map<String, Object> program : completedPrograms) {
                result.append("Title: ").append(program.get("title")).append("\n");
                result.append("ID: ").append(program.get("id")).append("\n");
                result.append("Status: ").append(program.get("status")).append("\n\n");
            }

            //return result.toString();
            return "Programs found successfully";

        } catch (IOException e) {
            logger.severe("Error reading or writing the JSON file: " + e.getMessage());
            return "Error reading or writing the JSON file: " + e.getMessage();
        } catch (ClassCastException e) {
            logger.severe("Error processing the data structure: " + e.getMessage());
            return "Error processing the data structure: " + e.getMessage();
        }
    }


    public String reviewProgram(String programName, String userName, String rating, String review) {
        if (Integer.parseInt(rating) > 10 || Integer.parseInt(rating) <= 0) {
            return "Invalid rating. Please try again.";
        }
        if (review == null || review.trim().isEmpty()) {
            return "Invalid review. Please try again.";
        }

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();

            List<Map<String, Object>> reviews = (List<Map<String, Object>>) data.get("reviews");
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");


            if (reviews == null) {
                reviews = new ArrayList<>();
                data.put("reviews", reviews);
            }
            if (programs == null) {
                return "Program does not exist or is not completed.";
            }
            int programId = -1;
            for (Map<String, Object> program : programs) {
                if (programName.equals(program.get("title"))) {
                    if ("completed".equals(program.get("status"))) {
                        programId = Integer.parseInt(program.get("id").toString());
                        break;
                    } else {
                        return "Program does not exist or is not completed.";
                    }
                }
            }
            if (programId == -1) {
                return "Program does not exist or is not completed.";
            }
            Map<String, Object> newReview = new HashMap<>();
            newReview.put("programId", programId);
            newReview.put("userName", userName);
            newReview.put("rating", Integer.parseInt(rating));
            newReview.put("review", review);
            reviews.add(newReview);

            data.put("reviews", reviews);
            JsonFileHandler.saveJsonData(data);
            return "Review submitted successfully";

        } catch (IOException e) {
            logger.severe("Error reading or writing the JSON file: " + e.getMessage());
            return "Error reading or writing the JSON file: " + e.getMessage();
        } catch (ClassCastException e) {
            logger.severe("Error processing the data structure: " + e.getMessage());
            return "Error processing the data structure: " + e.getMessage();
        }
    }
}