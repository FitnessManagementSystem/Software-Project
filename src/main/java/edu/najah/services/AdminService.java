package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.*;

import java.util.logging.Logger;

public class AdminService {
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    public String addInstructor(String name, String password, String role) {
        if (name.trim().isEmpty()) {
            return "Instructor name is empty";
        }
        if (password.trim().isEmpty()) {
            return "Password is empty";
        }
        if (role.trim().isEmpty()) {
            return "Role is empty";
        }

        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            List<Map<String, Object>> instructorsAwaitingApproval = (List<Map<String, Object>>) data.get("instructorsAwaitingApproval");
            if (instructorsAwaitingApproval == null) {
                instructorsAwaitingApproval = new java.util.ArrayList<>();
                data.put("instructorsAwaitingApproval", instructorsAwaitingApproval);
            }


            for (Map<String, Object> instructor : instructorsAwaitingApproval) {
                if (instructor.get("name").equals(name)) {
                    return "Account for instructor already exists with this name!";
                }
            }


            Map<String, Object> newInstructor = new java.util.HashMap<>();
            newInstructor.put("name", name);
            newInstructor.put("password", password);
            newInstructor.put("role", role);
            newInstructor.put("status", "created");

            newInstructor.put("active", true); // Set active flag to true by default

            instructorsAwaitingApproval.add(newInstructor);

            JsonFileHandler.saveJsonData(data);

            return "Instructor created successfully!";
        } catch (IOException e) {
            System.err.println("Error saving instructor: " + name + " - " + e.getMessage());
            return "Error saving instructor: " + name + " - " + e.getMessage();
        }
    }

    public String addClient(String name, String password, String role) {
        if (name.trim().isEmpty()) {
            return "Client name is empty";
        }
        if (password.trim().isEmpty()) {
            return "Password is empty";
        }
        if (role.trim().isEmpty()) {
            return "Role is empty";
        }

        Map<String, Object> data;
        try {
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");
            if (users == null) {
                users = new java.util.ArrayList<>();
                data.put("users", users);
            }

            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name)) {
                    return "Account for client already exists with this name!";
                }
            }

            Map<String, Object> newClient = new java.util.HashMap<>();
            newClient.put("name", name);
            newClient.put("password", password);
            newClient.put("role", role);
            newClient.put("active", true); // Set active flag to true by default

            users.add(newClient);

            JsonFileHandler.saveJsonData(data);

            return "Client created successfully!";
        } catch (IOException e) {
            System.err.println("Error saving client: " + name + " - " + e.getMessage());
            return "Error saving client: " + name + " - " + e.getMessage();
        }
    }


    public String deactivateClient(String name) {
        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> users = getOrCreateList(data, "users");
            List<Map<String, Object>> deactivatedUsers = getOrCreateList(data, "deactivatedUsers");

            Map<String, Object> userToDeactivate = users.stream()
                    .filter(user -> name.equals(user.get("name")) && "Client".equals(user.get("role")))
                    .findFirst()
                    .orElse(null);

            if (userToDeactivate == null) {
                return "Account for client not found in the system!";
            }

            userToDeactivate.put("active", false);
            deactivatedUsers.add(userToDeactivate);
            users.remove(userToDeactivate);

            JsonFileHandler.saveJsonData(data);

            return "Account for client deactivated successfully!";
        } catch (IOException e) {
            System.err.println("Error deactivating client: " + name + " - " + e.getMessage());
            return "Error deactivating client: " + name + " - " + e.getMessage();
        }
    }


    public String deactivateInstructor(String name) {
        try {
            Map<String, Object> data = loadData();

            List<Map<String, Object>> users = getOrCreateList(data, "users");
            List<Map<String, Object>> instructorsAwaitingApproval = getOrCreateList(data, "instructorsAwaitingApproval");
            List<Map<String, Object>> deactivatedUsers = getOrCreateList(data, "deactivatedUsers");

            boolean instructorFound = isInstructorFound(name, users, deactivatedUsers, false);
            if (!instructorFound) {
                instructorFound = isInstructorFound(name, instructorsAwaitingApproval, deactivatedUsers, false);
            }

            if (!instructorFound) {
                return "Account for instructor not found in the system!";
            }

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            return "Account for instructor deactivated successfully!";
        } catch (IOException e) {
            System.err.println("Error deactivating instructor: " + name + " - " + e.getMessage());
            return "Error deactivating instructor: " + name + " - " + e.getMessage();
        }
    }


    private boolean isInstructorFound(String name, List<Map<String, Object>> instructorsAwaitingApproval, List<Map<String, Object>> deactivatedUsers, boolean instructorFound) {
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
        return instructorFound;
    }

    public String approveInstructor(String name) {
        try {
            // Load JSON data
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new HashMap<>();
            }

            // Retrieve the instructorsAwaitingApproval list
            List<Map<String, Object>> instructorsAwaitingApproval = getInstructorsAwaitingApproval(data);
            if (instructorsAwaitingApproval == null) {
                return "No instructors awaiting approval or incorrect data format.";
            }

            // Retrieve the users list
            List<Map<String, Object>> users = getUsersList(data);


            Map<String, Object> instructorToApprove = findInstructorToApprove(instructorsAwaitingApproval, name);
            if (instructorToApprove == null) {
                return "Instructor account not approved due to incomplete data!";
            }

            // Approve the instructor by setting the active flag to true
            instructorToApprove.put("active", true);

            // Add the instructor to the users list
            users.add(instructorToApprove);

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return success message
            return "Instructor account approved successfully!";
        } catch (IOException e) {
            System.err.println("Error approving instructor: " + name + " - " + e.getMessage());
            return "Error approving instructor: " + name + " - " + e.getMessage();
        }
    }

    private List<Map<String, Object>> getInstructorsAwaitingApproval(Map<String, Object> data) {
        Object instructorsObject = data.get("instructorsAwaitingApproval");
        if (instructorsObject instanceof List) {
            return (List<Map<String, Object>>) instructorsObject;
        }
        return null;
    }

    private List<Map<String, Object>> getUsersList(Map<String, Object> data) {
        Object usersObject = data.get("users");
        if (usersObject instanceof List) {
            return (List<Map<String, Object>>) usersObject;
        } else {
            List<Map<String, Object>> users = new ArrayList<>();
            data.put("users", users);
            return users;
        }
    }

    private boolean isInstructorActive(List<Map<String, Object>> users, String name) {
        return users.stream().anyMatch(user -> name.equals(user.get("name")));
    }

    private Map<String, Object> findInstructorToApprove(List<Map<String, Object>> instructorsAwaitingApproval, String name) {
        Iterator<Map<String, Object>> iterator = instructorsAwaitingApproval.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> instructor = iterator.next();
            if (name.equals(instructor.get("name"))) {
                iterator.remove(); // Remove from awaiting approval
                return instructor;
            }
        }
        return null;
    }

    public String generatePopularProgramsReport() {
        try {
            // Load the program data
            Map<String, Object> data = loadData();

            // Check if no data is found
            if (data.isEmpty()) {
                return "No programs or enrollment data available.";
            }

            // Retrieve or create the necessary lists
            List<Map<String, Object>> programs = getOrCreateList(data, "programs");
            List<Map<String, Object>> enrolledPrograms = getOrCreateList(data, "enrolledPrograms");

            // Check if programs or enrolledPrograms data is missing
            if (programs.isEmpty() || enrolledPrograms.isEmpty()) {
                return "No programs or enrollment data available.";
            }

            // Calculate the count of users per program
            Map<Integer, Integer> programUserCount = calculateProgramUserCount(enrolledPrograms);

            // Prepare the report data based on program and user count
            List<Map<String, Object>> reportData = prepareReportData(programs, programUserCount);

            // If no report data is available, return appropriate message
            if (reportData.isEmpty()) {
                return "No report data found.";
            }

            // Store the report data in the statisticsReports section
            data.put("statisticsReports", reportData);
            JsonFileHandler.saveJsonData(data);

            // Return success message
            return "The report is successfully generated and ready for viewing.";
        } catch (Exception e) {
            // Handle any unexpected errors
            System.err.println("Error generating the report: " + e.getMessage());
            return "An error occurred while generating the report.";
        }
    }

    private Map<Integer, Integer> calculateProgramUserCount(List<Map<String, Object>> enrolledPrograms) {
        Map<Integer, Integer> programUserCount = new HashMap<>();
        for (Map<String, Object> enrollment : enrolledPrograms) {
            Integer programId = (Integer) enrollment.get("program_id");
            if (programId != null) {
                programUserCount.put(programId, programUserCount.getOrDefault(programId, 0) + 1);
            }
        }
        return programUserCount;
    }

    private List<Map<String, Object>> prepareReportData(List<Map<String, Object>> programs, Map<Integer, Integer> programUserCount) {
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

        // Sort by user count in descending order
        reportData.sort((p1, p2) -> Integer.compare((Integer) p2.get("userCount"), (Integer) p1.get("userCount")));
        return reportData;
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

            // Move all feedbacks from feedback list to handledFeedbacks
            for (Map<String, String> feedbackItem : feedback) {
                // Ensure the feedback status is set as handled
                feedbackItem.put("status", "handled");
                handledFeedbacks.add(feedbackItem);
            }

            // Clear the feedback list (since all feedbacks are now handled)
            feedback.clear();

            // Save the updated data back to the JSON file
            JsonFileHandler.saveJsonData(data);

            // Return a success message
            return "Feedback from " + instructorName + " to " + clientName + " handled successfully!";
        } catch (IOException e) {
            System.err.println("Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage());
            return "Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage();
        }
    }


    public int getActiveProgramsCount() {
        try {
            Map<String, Object> data = loadData();

            List<Map<String, Object>> programs = getOrCreateList(data, "programs");

            long activeCount = programs.stream()
                    .filter(program -> "active".equalsIgnoreCase((String) program.get("status")))
                    .count();

            return (int) activeCount;
        } catch (Exception e) {
            System.err.println("Error fetching active programs count: " + e.getMessage());
            return 0; // Return 0 in case of an exception
        }
    }


    public int getCompletedProgramsCount() {
        try {
            // Load JSON data using the helper method
            Map<String, Object> data = loadData();

            // Retrieve the programs list
            List<Map<String, Object>> programs = getOrCreateList(data, "programs");

            // Count completed programs using stream API
            long completedCount = programs.stream()
                    .filter(program -> "completed".equalsIgnoreCase((String) program.get("status")))
                    .count();

            return (int) completedCount;
        } catch (Exception e) {
            System.err.println("Error fetching completed programs count: " + e.getMessage());
            return 0; // Return 0 in case of an exception
        }
    }


    public String updatePlanStatus(String selectedPlanType, String selectedUserType, String newStatus) {
        try {
            Map<String, Object> data = loadData();

            List<Map<String, Object>> subscriptions = getOrCreateList(data, "subscriptions");

            for (Map<String, Object> subscription : subscriptions) {
                String userType = (String) subscription.get("userType");
                String planType = (String) subscription.get("planType");

                if (userType.equals(selectedUserType) && planType.equals(selectedPlanType)) {
                    subscription.put("status", newStatus); // Update plan status
                    // Save updated data to JSON file
                    JsonFileHandler.saveJsonData(data);
                    return String.format("The %s subscription plan for %s has been %s successfully.", selectedPlanType, selectedUserType, newStatus);
                }
            }
            return "Plan not found for the specified user type.";
        } catch (IOException e) {
            return String.format("Error updating plan status: %s", e.getMessage());
        }
    }


    public String deactivatePlan(String selectedPlanType, String selectedUserType) {
        return updatePlanStatus(selectedPlanType, selectedUserType, "inactive");
    }

    public String activatePlan(String selectedPlanType, String selectedUserType) {
        return updatePlanStatus(selectedPlanType, selectedUserType, "active");
    }


    public String getPlanStatus(String planType, String userType) {
        // Load the JSON data
        Map<String, Object> data = loadData();

        // Retrieve the subscriptions table
        List<Map<String, Object>> subscriptions = getOrCreateList(data, "subscriptions");

        // Search for the matching subscription
        for (Map<String, Object> subscription : subscriptions) {
            String storedUserType = (String) subscription.get("userType");
            String storedPlanType = (String) subscription.get("planType");

            if (storedUserType != null && storedPlanType != null &&
                    storedUserType.equalsIgnoreCase(userType) &&
                    storedPlanType.equalsIgnoreCase(planType)) {

                return subscription.getOrDefault("status", "Error: Status field is missing for the selected plan.").toString();
            }
        }

        return "Plan not found for the specified user type and plan type.";
    }


    public Map<String, Object> loadData() {
        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            logger.severe("data not found");
            return new HashMap<>();
        }

    }

    private List<Map<String, Object>> getOrCreateList(Map<String, Object> data, String key) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(key);
        if (list == null) {
            list = new ArrayList<>();
            data.put(key, list);
        }
        return list;
    }

}