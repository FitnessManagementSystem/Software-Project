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


    public String approveContent(String contentType, String actionType) {
        Map<String, Object> data;
        try {
            // Load the JSON data
            data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new java.util.HashMap<>();
            }

            // Retrieve the content approval scenarios from the JSON
            Object contentApprovalObject = data.get("contentApprovalScenarios");
            if (contentApprovalObject == null || !(contentApprovalObject instanceof List)) {
                return "No content approval scenarios available or incorrect data format";
            }

            List<Map<String, String>> contentApprovalScenarios = (List<Map<String, String>>) contentApprovalObject;

            // Search for the scenario matching the content type and action
            boolean actionPerformed = false;
            for (Map<String, String> scenario : contentApprovalScenarios) {
                if (scenario.get("Content Type").equals(contentType) && scenario.get("Approval Action").equalsIgnoreCase(actionType)) {
                    actionPerformed = true;
                    break;
                }
            }

            if (!actionPerformed) {
                return "Invalid action type or content type. Approval action or content type not found.";
            }

            // Get the content awaiting approval
            Object contentObject = data.get("contentAwaitingApproval");
            if (contentObject == null || !(contentObject instanceof List)) {
                return "No content awaiting approval or incorrect data format";
            }
            List<Map<String, String>> contentAwaitingApproval = (List<Map<String, String>>) contentObject;

            // Get the approved and denied content lists
            Object approvedObject = data.get("approvedContent");
            Object deniedObject = data.get("deniedContent");

            if (approvedObject == null || !(approvedObject instanceof List)) {
                approvedObject = new java.util.ArrayList<>();
                data.put("approvedContent", approvedObject);
            }
            if (deniedObject == null || !(deniedObject instanceof List)) {
                deniedObject = new java.util.ArrayList<>();
                data.put("deniedContent", deniedObject);
            }

            List<Map<String, String>> approvedContent = (List<Map<String, String>>) approvedObject;
            List<Map<String, String>> deniedContent = (List<Map<String, String>>) deniedObject;

            // Find the content in the awaiting approval list
            boolean contentFound = false;
            Map<String, String> contentToMove = null;
            for (Map<String, String> content : contentAwaitingApproval) {
                if (content.get("type").equals(contentType)) {
                    contentToMove = content;
                    contentFound = true;
                    break;
                }
            }

            if (!contentFound) {
                return "Content not found in the awaiting approval list!";
            }

            // Perform the approval or rejection based on actionType
            if ("approve".equalsIgnoreCase(actionType)) {
                approvedContent.add(contentToMove);
            } else if ("reject".equalsIgnoreCase(actionType)) {
                deniedContent.add(contentToMove);
            } else {
                return "Invalid action type. Only 'approve' or 'reject' are valid.";
            }

            // Remove the content from the awaiting approval list
            contentAwaitingApproval.remove(contentToMove);

            // Save the updated data
            JsonFileHandler.saveJsonData(data);

            return "Content " + actionType + "d successfully!";
        } catch (IOException e) {
            return "Error approving/rejecting content: " + contentType + " - " + e.getMessage();
        }
    }


}
