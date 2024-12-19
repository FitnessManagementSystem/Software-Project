package edu.najah.Services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class Client {
    private String clientName;
    private String clientPreferenceValue;
    private String clientRestrictionValue;
    private String fitnessGoalValue;
    private int ageValue;
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    public void setDietaryPreference(String preferenceValue) {
        clientPreferenceValue = preferenceValue;
    }

    public void setDietaryRestrictions(String restrictionValue) {
        clientRestrictionValue = restrictionValue;
    }

    public void setName(String name) {
        clientName = name;
    }

    public void setFitnessGoal(String fitnessGoal) {
        fitnessGoalValue = fitnessGoal;
    }

    public void setAge(int age) {
        ageValue = age;
    }


    public String getName() {
        return clientName;
    }

    public String getDietaryPreference() {
        return clientPreferenceValue;
    }

    public String getDietaryRestrictions() {
        return clientRestrictionValue;
    }

    public String getFitnessGoal() {
        return new String(fitnessGoalValue);
    }

    public int getAge() {
        return Integer.valueOf(ageValue);
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

}
