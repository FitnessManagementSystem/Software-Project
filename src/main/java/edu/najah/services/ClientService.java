package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service class for managing clients, programs, and related operations.
 */

public class ClientService {
    public static final String PROGRAM_ID = "programId";
    public static final String STATUS = "status";
    public static final String USER_NAME = "userName";
    public static final String TITLE = "title";
    public static final String ERROR_READING_OR_WRITING_THE_JSON_FILE = "Error reading or writing the JSON file: ";


    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    private static void getCompletedPrograms(String userName, List<Map<String, Object>> programs, List<Map<String, Object>> enrolledUsers, List<Map<String, Object>> completedPrograms) {
        for (Map<String, Object> program : programs) {
            for (Map<String, Object> enrolledUser : enrolledUsers) {
                if (program.get("id").equals(enrolledUser.get(PROGRAM_ID))) {
                    if ("active".equals(program.get(STATUS))) {
                        logger.warning("Program is still active");
                        continue;
                    }
                    if (enrolledUser.get(USER_NAME).equals(userName)) {
                        completedPrograms.add(program);
                    }
                }
            }
        }
    }

    /**
     * Creates a new user profile.
     *
     * @param name               The name of the user.
     * @param dietaryPreference  The dietary preference of the user.
     * @param dietaryRestriction The dietary restriction of the user.
     * @param age                The age of the user.
     * @param fitnessGoal        The fitness goal of the user.
     * @return A message indicating the outcome of the operation.
     */
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

    /**
     * Searches programs by a specific filter type.
     *
     * @param filterType The filter type to apply.
     * @return A list of matching programs for the specified filter type.
     */
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

    /**
     * Enrolls a user in a program.
     *
     * @param programName The name of the program to enroll in.
     * @param userName    The name of the user enrolling in the program.
     * @return A message indicating the outcome of the enrollment.
     */
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
            logger.severe(ERROR_READING_OR_WRITING_THE_JSON_FILE + e.getMessage());
            return ERROR_READING_OR_WRITING_THE_JSON_FILE + e.getMessage();
        }
    }

    /**
     * Retrieves a list of completed programs for a specific user.
     *
     * @param userName The name of the user.
     * @return A list of completed programs.
     */
    public List<Map<String, Object>> getCompletedProgramsList(String userName) {
        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> programs = getPrograms(data);
            List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);

            if (programs.isEmpty()) {
                logger.warning("No programs available");
                return new ArrayList<>();
            }

            if (enrolledUsers.isEmpty()) {
                logger.warning("No enrolled users available");
                return new ArrayList<>();
            }

            List<Map<String, Object>> completedPrograms = new ArrayList<>();

            getCompletedPrograms(userName, programs, enrolledUsers, completedPrograms);

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

    /**
     * Submits a review for a program.
     *
     * @param programName The name of the program being reviewed.
     * @param userName    The name of the user submitting the review.
     * @param rating      The rating provided for the program.
     * @param review      The review text.
     * @return A message indicating the outcome of the review submission.
     */
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
            logger.severe(ERROR_READING_OR_WRITING_THE_JSON_FILE + e.getMessage());
            return ERROR_READING_OR_WRITING_THE_JSON_FILE + e.getMessage();
        }
    }

    /**
     * Loads data from the JSON file.
     *
     * @return A map containing the loaded data.
     * @throws IOException If an error occurs while loading the data.
     */
    private Map<String, Object> loadData() throws IOException {
        return JsonFileHandler.loadJsonData();
    }

    /**
     * Saves data to the JSON file.
     *
     * @param data The data to save.
     */
    private void saveData(Map<String, Object> data) {
        try {
            JsonFileHandler.saveJsonData(data);
        } catch (IOException e) {
            logger.severe("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Retrieves user profiles from the data map.
     *
     * @param data The data map.
     * @return A map of user profiles.
     */
    private Map<String, Map<String, String>> getUserProfiles(Map<String, Object> data) {
        return (Map<String, Map<String, String>>) data.getOrDefault("userProfiles", new HashMap<>());
    }

    /**
     * Retrieves programs from the data map.
     *
     * @param data The data map.
     * @return A list of programs in the json file.
     */
    private List<Map<String, Object>> getPrograms(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("programs", new ArrayList<>());
    }

    /**
     * Retrieves enrolled users from the data map.
     *
     * @param data The data map.
     * @return A list of enrolled users in the json file.
     */
    private List<Map<String, Object>> getEnrolledUsers(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("enrolledUsers", new ArrayList<>());
    }

    /**
     * Retrieves reviews from the data map.
     *
     * @param data The data map.
     * @return A list of reviews from the json file.
     */
    private List<Map<String, Object>> getReviews(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.getOrDefault("reviews", new ArrayList<>());
    }

    /**
     * Creates a map of profile details for a user.
     *
     * @param name               The user's name.
     * @param dietaryPreference  The user's dietary preference.
     * @param dietaryRestriction The user's dietary restriction.
     * @param age                The user's age.
     * @param fitnessGoal        The user's fitness goal.
     * @return A map of profile details.
     */
    private Map<String, String> createProfileDetails(String name, String dietaryPreference, String dietaryRestriction, int age, String fitnessGoal) {
        Map<String, String> profileDetails = new HashMap<>();
        profileDetails.put("name", name);
        profileDetails.put("dietaryPreference", dietaryPreference);
        profileDetails.put("dietaryRestriction", dietaryRestriction);
        profileDetails.put("age", String.valueOf(age));
        profileDetails.put("fitnessGoal", fitnessGoal);
        return profileDetails;
    }

    /**
     * Checks if a user exists in the data map.
     *
     * @param data     The data map.
     * @param userName The name of the user to check.
     * @return True if the user exists, false otherwise.
     */
    private boolean isUserExists(Map<String, Object> data, String userName) {
        Map<String, Object> users = (Map<String, Object>) data.get("users");
        return users.containsKey(userName);
    }

    /**
     * Checks if a user is enrolled in a specific program.
     *
     * @param data        The data map.
     * @param userName    The user's name.
     * @param programName The program's name.
     * @return True if the user is enrolled in the program, false otherwise.
     */
    private boolean isUserEnrolledInProgram(Map<String, Object> data, String userName, String programName) {
        List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);
        for (Map<String, Object> enrollment : enrolledUsers) {
            if (userName.equals(enrollment.get(USER_NAME)) && programName.equals(enrollment.get("programName"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the ID of a program based on its name.
     *
     * @param data        The data map.
     * @param programName The name of the program.
     * @return The program ID, or null if not found, or -1 if the program is completed.
     */
    private Integer getProgramId(Map<String, Object> data, String programName) {
        List<Map<String, Object>> programs = getPrograms(data);
        for (Map<String, Object> program : programs) {
            if (programName.equals(program.get(TITLE)) && "active".equals(program.get(STATUS))) {
                return (Integer) program.get("id");
            }
            if (programName.equals(program.get(TITLE)) && "completed".equals(program.get(STATUS))) {
                return -1;
            }
        }
        return null;
    }

    /**
     * Enrolls a user in a program.
     *
     * @param data        The data map.
     * @param userName    The user's name.
     * @param programName The program's name.
     * @param programId   The program ID.
     */
    private void enrollUserInProgram(Map<String, Object> data, String userName, String programName, Integer programId) {
        List<Map<String, Object>> enrolledUsers = getEnrolledUsers(data);
        Map<String, Object> userEnrollment = new HashMap<>();
        userEnrollment.put(PROGRAM_ID, programId);
        userEnrollment.put("programName", programName);
        userEnrollment.put(USER_NAME, userName);
        enrolledUsers.add(userEnrollment);
    }

    /**
     * Validates the rating input.
     *
     * @param rating The rating input.
     * @return True if the rating is invalid, false otherwise.
     */
    private boolean isInvalidRating(String rating) {
        int rate = Integer.parseInt(rating);
        return rate <= 0 || rate > 10;
    }

    /**
     * Validates the review input.
     *
     * @param review The review input.
     * @return True if the review is invalid, false otherwise.
     */
    private boolean isInvalidReview(String review) {
        return review == null || review.trim().isEmpty();
    }

    /**
     * Finds the ID of a completed program by its name.
     *
     * @param programs    The list of programs.
     * @param programName The name of the program.
     * @return The program ID, or -1 if not found.
     */
    private int findProgramId(List<Map<String, Object>> programs, String programName) {
        for (Map<String, Object> program : programs) {
            if (programName.equals(program.get(TITLE)) && "completed".equals(program.get(STATUS))) {
                return (Integer) program.get("id");
            }
        }
        return -1;
    }

    /**
     * Adds a review for a program.
     *
     * @param reviews   The list of reviews.
     * @param programId The program ID.
     * @param userName  The user's name.
     * @param rating    The rating for the program.
     * @param review    The review text.
     */
    private void addReview(List<Map<String, Object>> reviews, int programId, String userName, String rating, String review) {
        Map<String, Object> newReview = new HashMap<>();
        newReview.put(PROGRAM_ID, programId);
        newReview.put(USER_NAME, userName);
        newReview.put("rating", Integer.parseInt(rating));
        newReview.put("review", review);
        reviews.add(newReview);
    }
}
