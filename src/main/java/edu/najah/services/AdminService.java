package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {

    public String addInstructor(String name, String password, String role) {
        // Validate input parameters
        if (name == null || name.isEmpty()) {
            return "Instructor name is empty";
        }
        if (password == null || password.isEmpty()) {
            return "Password is empty";
        }
        if (role == null || role.isEmpty()) {
            return "Role is empty";
        }

        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the instructors list
            List<Map<String, Object>> instructorsAwaitingApproval = (List<Map<String, Object>>) data.get("instructorsAwaitingApproval");
            if (instructorsAwaitingApproval == null) {
                instructorsAwaitingApproval = new java.util.ArrayList<>();
                data.put("instructorsAwaitingApproval", instructorsAwaitingApproval);
            }

            // Check if the instructor already exists
            for (Map<String, Object> instructor : instructorsAwaitingApproval) {
                if (instructor.get("name").equals(name)) {
                    return "Account for instructor already exists with this name!";
                }
            }

            // Add the new instructor
            Map<String, Object> newInstructor = new java.util.HashMap<>();
            newInstructor.put("name", name);
            newInstructor.put("password", password);
            newInstructor.put("role", role);
            newInstructor.put("active", true); // Set active flag to true by default

            instructorsAwaitingApproval.add(newInstructor);

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Instructor created successfully!";
        } catch (IOException e) {
            System.err.println("Error saving instructor: " + name + " - " + e.getMessage());
            return "Error saving instructor: " + name + " - " + e.getMessage();
        }
    }

    public String addClient(String name, String password, String role) {
        if (name == null || name.isEmpty()) {
            return "Client name is empty";
        }
        if (password == null || password.isEmpty()) {
            return "Password is empty";
        }
        if (role == null || role.isEmpty()) {
            return "Role is empty";
        }

        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the users list
            List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");
            if (users == null) {
                users = new java.util.ArrayList<>();
                data.put("users", users);
            }

            // Check if the client already exists
            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name)) {
                    return "Account for client already exists with this name!";
                }
            }

            // Add the new client
            Map<String, Object> newClient = new java.util.HashMap<>();
            newClient.put("name", name);
            newClient.put("password", password);
            newClient.put("role", role);
            newClient.put("active", true); // Set active flag to true by default

            users.add(newClient);

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Client created successfully!";
        } catch (IOException e) {
            System.err.println("Error saving client: " + name + " - " + e.getMessage());
            return "Error saving client: " + name + " - " + e.getMessage();
        }
    }


    public String deactivateClient(String name) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the users list, check if it's a List
            Object usersObject = data.get("users");
            if (usersObject == null || !(usersObject instanceof List)) {
                return "No users found or incorrect data format";
            }
            List<Map<String, Object>> users = (List<Map<String, Object>>) usersObject;

            // Retrieve the deactivatedUsers list, check if it's a List
            Object deactivatedUsersObject = data.get("deactivatedUsers");
            if (deactivatedUsersObject == null || !(deactivatedUsersObject instanceof List)) {
                deactivatedUsersObject = new java.util.ArrayList<>();
                data.put("deactivatedUsers", deactivatedUsersObject);
            }
            List<Map<String, Object>> deactivatedUsers = (List<Map<String, Object>>) deactivatedUsersObject;

            // Find the client in the users list and deactivate
            boolean userFound = false;
            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name) && user.get("role").equals("Client")) {
                    // Deactivate and move to deactivatedUsers
                    user.put("active", false);
                    deactivatedUsers.add(user);
                    users.remove(user);
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                return "Account for client not found in the system!";
            }

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return success message for client deactivation
            return "Account for client deactivated successfully!";
        } catch (IOException e) {
            System.err.println("Error deactivating client: " + name + " - " + e.getMessage());
            return "Error deactivating client: " + name + " - " + e.getMessage();
        }
    }

    public String deactivateInstructor(String name) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the users list, check if it's a List
            Object usersObject = data.get("users");
            if (usersObject == null || !(usersObject instanceof List)) {
                return "No users found or incorrect data format";
            }
            List<Map<String, Object>> users = (List<Map<String, Object>>) usersObject;

            // Retrieve the instructorsAwaitingApproval list, check if it's a List
            Object instructorsObject = data.get("instructorsAwaitingApproval");
            if (instructorsObject == null || !(instructorsObject instanceof List)) {
                return "No instructors awaiting approval or incorrect data format";
            }
            List<Map<String, Object>> instructorsAwaitingApproval = (List<Map<String, Object>>) instructorsObject;

            // Retrieve the deactivatedUsers list, check if it's a List
            Object deactivatedUsersObject = data.get("deactivatedUsers");
            if (deactivatedUsersObject == null || !(deactivatedUsersObject instanceof List)) {
                deactivatedUsersObject = new java.util.ArrayList<>();
                data.put("deactivatedUsers", deactivatedUsersObject);
            }
            List<Map<String, Object>> deactivatedUsers = (List<Map<String, Object>>) deactivatedUsersObject;

            // First, check in the users list
            boolean instructorFound = false;
            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name) && user.get("role").equals("Instructor")) {
                    // Deactivate and move to deactivatedUsers
                    user.put("active", false);
                    deactivatedUsers.add(user);
                    users.remove(user);
                    instructorFound = true;
                    break;
                }
            }

            // If not found in users, check in instructorsAwaitingApproval list
            if (!instructorFound) {
                for (Map<String, Object> instructor : instructorsAwaitingApproval) {
                    if (instructor.get("name").equals(name) && instructor.get("role").equals("Instructor")) {
                        // Deactivate and move to deactivatedUsers
                        instructor.put("active", false);
                        deactivatedUsers.add(instructor);
                        instructorsAwaitingApproval.remove(instructor);
                        instructorFound = true;
                        break;
                    }
                }
            }

            if (!instructorFound) {
                return "Account for instructor not found in the system!";
            }

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return success message for instructor deactivation
            return "Account for instructor deactivated successfully!";
        } catch (IOException e) {
            System.err.println("Error deactivating instructor: " + name + " - " + e.getMessage());
            return "Error deactivating instructor: " + name + " - " + e.getMessage();
        }
    }


    public String approveInstructor(String name) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the instructorsAwaitingApproval list
            Object instructorsObject = data.get("instructorsAwaitingApproval");
            if (instructorsObject == null || !(instructorsObject instanceof List)) {
                return "No instructors awaiting approval or incorrect data format";
            }
            List<Map<String, Object>> instructorsAwaitingApproval = (List<Map<String, Object>>) instructorsObject;

            // Retrieve the users list
            Object usersObject = data.get("users");
            if (usersObject == null || !(usersObject instanceof List)) {
                usersObject = new java.util.ArrayList<>();
                data.put("users", usersObject);
            }
            List<Map<String, Object>> users = (List<Map<String, Object>>) usersObject;

            // Check if the instructor is already an active user
            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name)) {
                    return "Instructor is already an active user and cannot be approved again.";
                }
            }

            // Find the instructor in the instructorsAwaitingApproval list
            boolean instructorFound = false;
            Map<String, Object> instructorToApprove = null;
            for (Map<String, Object> instructor : instructorsAwaitingApproval) {
                if (instructor.get("name").equals(name)) {
                    instructorToApprove = instructor;
                    instructorsAwaitingApproval.remove(instructor);  // Remove from awaiting approval
                    instructorFound = true;
                    break;
                }
            }

            if (!instructorFound) {
                return "Instructor account not approved due to incomplete data!";
            }

            // Set active flag to true for the approved instructor
            instructorToApprove.put("active", true);

            // Add the instructor to the users list
            users.add(instructorToApprove);

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return success message
            return "instructor account approved successfully!";
        } catch (IOException e) {
            System.err.println("Error approving instructor: " + name + " - " + e.getMessage());
            return "Error approving instructor: " + name + " - " + e.getMessage();
        }
    }

    public String generatePopularProgramsReport() {
        try {
            // Load JSON data
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "An error occurred";
            }

            // Retrieve the programs and enrolled programs list from the data
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            List<Map<String, Object>> enrolledPrograms = (List<Map<String, Object>>) data.get("enrolledProgrames");

            if (programs == null || enrolledPrograms == null) {
                return "An error occurred";
            }

            // Initialize a map to track the number of users for each program
            Map<Integer, Integer> programUserCount = new HashMap<>();

            // Populate the programUserCount map with enrollment data
            for (Map<String, Object> enrollment : enrolledPrograms) {
                Integer programId = (Integer) enrollment.get("program_id");
                if (programId != null) {
                    programUserCount.put(programId, programUserCount.getOrDefault(programId, 0) + 1);
                }
            }

            // Prepare a list of program details along with their user count
            List<Map<String, Object>> reportData = new ArrayList<>();
            for (Map<String, Object> program : programs) {
                Integer programId = (Integer) program.get("id");
                String title = (String) program.get("title");
                Integer userCount = programUserCount.getOrDefault(programId, 0);

                Map<String, Object> programReport = new HashMap<>();
                programReport.put("program", title);
                programReport.put("userCount", userCount);
                reportData.add(programReport);
            }

            // Sort the report data by user count in descending order
            reportData.sort((p1, p2) -> Integer.compare((Integer) p2.get("userCount"), (Integer) p1.get("userCount")));

            // Check if the report is empty
            if (reportData.isEmpty()) {
                return "An error occurred";
            }

            // Add the report data to the statisticsReports table
            data.put("statisticsReports", reportData);

            // Save the updated JSON data back to the file
            JsonFileHandler.saveJsonData(data);

            // Return success message
            return "The report is successfully generated";

        } catch (Exception e) {
            System.err.println("Error generating the report: " + e.getMessage());
            return "An error occurred"; // Return error for exceptions
        }
    }

    public String handleFeedback(String instructorName, String clientName) {
        Map<String, Object> data;
        try {
            // Check if the instructorName or clientName is empty
            if (instructorName == null || instructorName.trim().isEmpty() || clientName == null || clientName.trim().isEmpty()) {
                return "Feedback not handled"; // Return early if either name is empty
            }

            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the feedback list
            Object feedbackObject = data.get("feedback");
            if (feedbackObject == null || !(feedbackObject instanceof List)) {
                return "No feedback available or incorrect data format";
            }
            List<Map<String, String>> feedback = (List<Map<String, String>>) feedbackObject;

            // Retrieve the handledFeedbacks list
            Object handledFeedbacksObject = data.get("handledFeedbacks");
            if (handledFeedbacksObject == null || !(handledFeedbacksObject instanceof List)) {
                handledFeedbacksObject = new java.util.ArrayList<>();
                data.put("handledFeedbacks", handledFeedbacksObject);
            }
            List<Map<String, String>> handledFeedbacks = (List<Map<String, String>>) handledFeedbacksObject;

            // Find the feedback in the feedback list
            boolean feedbackFound = false;
            Map<String, String> feedbackToHandle = null;
            for (Map<String, String> feedbackItem : feedback) {
                // Ensure the feedback status is "not handled" initially (if not already set)
                if (feedbackItem.get("status") == null) {
                    feedbackItem.put("status", "not handled");
                }

                // Check if this is the feedback we need to handle
                if (feedbackItem.get("from").equals(instructorName) && feedbackItem.get("to").equals(clientName)) {
                    feedbackToHandle = feedbackItem;
                    feedbackFound = true;
                    break;
                }
            }

            if (!feedbackFound) {
                return "Feedback from " + instructorName + " to " + clientName + " not found";
            }

            // Now that feedback is found, mark it as handled
            feedbackToHandle.put("status", "handled");
            handledFeedbacks.add(feedbackToHandle);
            feedback.remove(feedbackToHandle); // Remove from the feedback list

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return success message
            return "Feedback from " + instructorName + " to " + clientName + " handled successfully!";
        } catch (IOException e) {
            System.err.println("Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage());
            return "Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage();
        }
    }


    public int getActiveProgramsCount() {
        try {
            // Load JSON data
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return 0; // Return 0 if data is null
            }

            // Retrieve the programs list
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            if (programs == null) {
                return 0; // Return 0 if no programs found
            }

            // Count active programs
            int activeCount = 0;
            for (Map<String, Object> program : programs) {
                if ("active".equalsIgnoreCase((String) program.get("status"))) {
                    activeCount++;
                }
            }
            return activeCount;
        } catch (Exception e) {
            System.err.println("Error fetching active programs count: " + e.getMessage());
            return 0; // Return 0 in case of an exception
        }
    }

    public int getCompletedProgramsCount() {
        try {
            // Load JSON data
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return 0; // Return 0 if data is null
            }

            // Retrieve the programs list
            List<Map<String, Object>> programs = (List<Map<String, Object>>) data.get("programs");
            if (programs == null) {
                return 0; // Return 0 if no programs found
            }

            // Count completed programs
            int completedCount = 0;
            for (Map<String, Object> program : programs) {
                if ("completed".equalsIgnoreCase((String) program.get("status"))) {
                    completedCount++;
                }
            }
            return completedCount;
        } catch (Exception e) {
            System.err.println("Error fetching completed programs count: " + e.getMessage());
            return 0; // Return 0 in case of an exception
        }
    }

    public String deactivatePlan(String selectedPlanType, String selectedUserType) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "Error: Data could not be loaded.";
            }

            // Retrieve the subscriptions table
            List<Map<String, Object>> subscriptions = (List<Map<String, Object>>) data.get("subscriptions");
            if (subscriptions == null) {
                return "Error: No subscriptions found.";
            }

            // Find and deactivate the plan
            for (Map<String, Object> subscription : subscriptions) {
                String userType = (String) subscription.get("userType");
                String planType = (String) subscription.get("planType");
                if (userType.equals(selectedUserType) && planType.equals(selectedPlanType)) {
                    subscription.put("status", "inactive"); // Deactivate the plan
                    // Save updated data to JSON file
                    JsonFileHandler.saveJsonData(data);
                    return "The " + selectedPlanType + " subscription plan for " + selectedUserType + " has been deactivated successfully.";
                }
            }
            return "Plan not found for the specified user type.";
        } catch (IOException e) {
            return "Error deactivating plan: " + e.getMessage();
        }
    }

    public String activatePlan(String selectedPlanType, String selectedUserType) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "Error: Data could not be loaded.";
            }

            // Retrieve the subscriptions table
            List<Map<String, Object>> subscriptions = (List<Map<String, Object>>) data.get("subscriptions");
            if (subscriptions == null) {
                return "Error: No subscriptions found.";
            }

            // Find and activate the plan
            for (Map<String, Object> subscription : subscriptions) {
                String userType = (String) subscription.get("userType");
                String planType = (String) subscription.get("planType");
                if (userType.equals(selectedUserType) && planType.equals(selectedPlanType)) {
                    subscription.put("status", "active"); // Activate the plan
                    // Save updated data to JSON file
                    JsonFileHandler.saveJsonData(data);
                    return "The " + selectedPlanType + " subscription plan for " + selectedUserType + " has been activated successfully.";
                }
            }
            return "Plan not found for the specified user type.";
        } catch (IOException e) {
            return "Error activating plan: " + e.getMessage();
        }
    }

    public String getPlanStatus(String planType, String userType) {
        Map<String, Object> data;
        try {
            // Load JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                return "Error: Data could not be loaded.";
            }

            // Retrieve the subscriptions table
            List<Map<String, Object>> subscriptions = (List<Map<String, Object>>) data.get("subscriptions");
            if (subscriptions == null) {
                return "Error: No subscriptions found.";
            }

            // Check the status of the selected plan
            for (Map<String, Object> subscription : subscriptions) {
                String userTypeFromData = (String) subscription.get("userType");
                String planTypeFromData = (String) subscription.get("planType");

                // Ensure the comparison is case-insensitive
                if (userTypeFromData != null && planTypeFromData != null &&
                        userTypeFromData.equalsIgnoreCase(userType) && planTypeFromData.equalsIgnoreCase(planType)) {

                    // Check if the status exists and return it
                    Object status = subscription.get("status");
                    if (status != null) {
                        return (String) status;
                    } else {
                        return "Error: Status field is missing for the selected plan.";
                    }
                }
            }

            return "Plan not found for the specified user type and plan type.";
        } catch (IOException e) {
            return "Error getting plan status: " + e.getMessage();
        }
    }


}



