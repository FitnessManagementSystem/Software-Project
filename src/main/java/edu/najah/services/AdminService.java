package edu.najah.services;

import edu.najah.utilities.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AdminService {
    private static final Logger logger = Logger.getLogger(ClientService.class.getName());

    public String addInstructor(String name, String password, String role) {
        if (name.isEmpty()) {
            return "Instructor name is empty";
        }
        if (password.isEmpty()) {
            return "Password is empty";
        }
        if (role.isEmpty()) {
            return "Role is empty";
        }

        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> instructorsAwaitingApproval = getListFromData(data, "instructorsAwaitingApproval");

            if (isInstructorExists(instructorsAwaitingApproval, name)) {
                return "Account for instructor already exists with this name!";
            }

            Map<String, Object> newInstructor = createUserMap(name, password, role);
            instructorsAwaitingApproval.add(newInstructor);

            JsonFileHandler.saveJsonData(data);
            return "Instructor created successfully!";
        } catch (IOException e) {
            return handleError("instructor", name, e);
        }
    }

    public String addClient(String name, String password, String role) {
        if (name.isEmpty()) {
            return "Client name is empty";
        }
        if (password.isEmpty()) {
            return "Password is empty";
        }
        if (role.isEmpty()) {
            return "Role is empty";
        }

        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> users = getListFromData(data, "users");

            if (isClientExists(users, name)) {
                return "Account for client already exists with this name!";
            }

            Map<String, Object> newClient = createUserMap(name, password, role);
            users.add(newClient);

            JsonFileHandler.saveJsonData(data);
            return "Client created successfully!";
        } catch (IOException e) {
            return handleError("client", name, e);
        }
    }

    public String deactivateClient(String name) {
        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> users = getListFromData(data, "users");
            List<Map<String, Object>> deactivatedUsers = getListFromData(data, "deactivatedUsers");

            boolean userFound = deactivateUser(users, deactivatedUsers, name, "Client");

            if (!userFound) {
                return "Account for client not found in the system!";
            }

            JsonFileHandler.saveJsonData(data);
            return "Account for client deactivated successfully!";
        } catch (IOException e) {
            return handleError("client", name, e);
        }
    }

    public String deactivateInstructor(String name) {
        try {
            Map<String, Object> data = loadData();
            List<Map<String, Object>> users = getListFromData(data, "users");
            List<Map<String, Object>> instructorsAwaitingApproval = getListFromData(data, "instructorsAwaitingApproval");
            List<Map<String, Object>> deactivatedUsers = getListFromData(data, "deactivatedUsers");

            boolean instructorFound = deactivateUser(users, deactivatedUsers, name, "Instructor");

            if (!instructorFound) {
                instructorFound = deactivateUser(instructorsAwaitingApproval, deactivatedUsers, name, "Instructor");
            }

            if (!instructorFound) {
                return "Account for instructor not found in the system!";
            }

            JsonFileHandler.saveJsonData(data);
            return "Account for instructor deactivated successfully!";
        } catch (IOException e) {
            return handleError("instructor", name, e);
        }
    }

    private Map<String, Object> loadData() throws IOException {
        Map<String, Object> data = JsonFileHandler.loadJsonData();
        return (data != null) ? data : new HashMap<>();
    }

    private List<Map<String, Object>> getListFromData(Map<String, Object> data, String key) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(key);
        if (list == null) {
            list = new ArrayList<>();
            data.put(key, list);
        }
        return list;
    }

    private boolean isInstructorExists(List<Map<String, Object>> instructors, String name) {
        return isUserExists(instructors, name);
    }

    private boolean isClientExists(List<Map<String, Object>> clients, String name) {
        return isUserExists(clients, name);
    }

    private boolean isUserExists(List<Map<String, Object>> users, String name) {
        for (Map<String, Object> user : users) {
            if (user.get("name").equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> createUserMap(String name, String password, String role) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("password", password);
        user.put("role", role);
        user.put("active", true);
        return user;
    }

    private boolean deactivateUser(List<Map<String, Object>> users, List<Map<String, Object>> deactivatedUsers, String name, String role) {
        for (Map<String, Object> user : users) {
            if (user.get("name").equals(name) && user.get("role").equals(role)) {
                user.put("active", false);
                deactivatedUsers.add(user);
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    private String handleError(String type, String name, IOException e) {
        logger.severe(new StringBuilder().append("Error saving ").append(type).append(": ").append(name).append(" - ").append(e.getMessage()).toString());
        return "Error saving " + type + ": " + name + " - " + e.getMessage();
    }

    public String approveInstructor(String name) {
        Map<String, Object> data;
        try {
            data = loadData();
            List<Map<String, Object>> instructorsAwaitingApproval = (List<Map<String, Object>>) data.get("instructorsAwaitingApproval");

            List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");
            if (users == null) {
                users = new ArrayList<>();
                data.put("users", users);
            }

            for (Map<String, Object> user : users) {
                if (user.get("name").equals(name)) {
                    return "Instructor is already an active user and cannot be approved again.";
                }
            }

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

            instructorToApprove.put("active", true);

            users.add(instructorToApprove);

            JsonFileHandler.saveJsonData(data);

            return "Instructor account approved successfully!";
        } catch (IOException e) {
            return handleError("instructor", name, e);
        }
    }


    public String handleFeedback(String instructorName, String clientName) {
        Map<String, Object> data;
        try {
            if (instructorName.trim().isEmpty()) {
                return "Instructor name is empty";
            }
            if (clientName.trim().isEmpty()) {
                return "Client name is empty";
            }
            data = loadData();

            List<Map<String, String>> feedback = (List<Map<String, String>>) data.get("feedback");

            List<Map<String, String>> handledFeedbacks = (List<Map<String, String>>) data.get("handledFeedbacks");

            boolean feedbackFound = false;
            Map<String, String> feedbackToHandle = null;
            for (Map<String, String> feedbackItem : feedback) {
                feedbackItem.putIfAbsent("status", "not handled");
                if (feedbackItem.get("from").equals(instructorName) && feedbackItem.get("to").equals(clientName)) {
                    feedbackToHandle = feedbackItem;
                    feedbackFound = true;
                    break;
                }
            }

            if (!feedbackFound) {
                return "Specified feedback is not available";
            }

            feedbackToHandle.put("status", "handled");
            handledFeedbacks.add(feedbackToHandle);
            feedback.remove(feedbackToHandle);

            JsonFileHandler.saveJsonData(data);
            return "Feedback from " + instructorName + " to " + clientName + " handled successfully!";
        } catch (IOException e) {
            logger.severe("Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage());
            return "Error handling feedback from " + instructorName + " to " + clientName + " - " + e.getMessage();
        }
    }

    private Map<String, Object> loadJsonData() {
        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                throw new IOException("Data could not be loaded.");
            }
            return data;
        } catch (IOException e) {
            logger.severe("Error loading JSON data: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    private List<Map<String, Object>> getProgramsList(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.get("programs");
    }

    private List<Map<String, Object>> getSubscriptionsList(Map<String, Object> data) {
        return (List<Map<String, Object>>) data.get("subscriptions");
    }

    public int getActiveProgramsCount() {
        Map<String, Object> data = loadJsonData();
        if (data == null) {
            return 0;
        }

        List<Map<String, Object>> programs = getProgramsList(data);
        if (programs == null) {
            return 0;
        }

        int activeCount = 0;
        for (Map<String, Object> program : programs) {
            if ("active".equalsIgnoreCase((String) program.get("status"))) {
                activeCount++;
            }
        }
        return activeCount;
    }

    public int getCompletedProgramsCount() {
        Map<String, Object> data = loadJsonData();
        if (data == null) return 0;

        List<Map<String, Object>> programs = getProgramsList(data);
        if (programs == null) return 0;

        int completedCount = 0;
        for (Map<String, Object> program : programs) {
            if ("completed".equalsIgnoreCase((String) program.get("status"))) {
                completedCount++;
            }
        }
        return completedCount;
    }

    public String deactivatePlan(String selectedPlanType, String selectedUserType) {
        Map<String, Object> data = loadJsonData();

        List<Map<String, Object>> subscriptions = getSubscriptionsList(data);

        for (Map<String, Object> subscription : subscriptions) {
            String userType = (String) subscription.get("userType");
            String planType = (String) subscription.get("planType");
            if (userType.equals(selectedUserType) && planType.equals(selectedPlanType)) {
                subscription.put("status", "inactive");
                try {
                    JsonFileHandler.saveJsonData(data);
                } catch (IOException e) {
                    return "Error saving data: " + e.getMessage();
                }
                return "The " + selectedPlanType + " subscription plan for " + selectedUserType + " has been deactivated successfully.";
            }
        }
        return "Plan not found for the specified user type.";
    }

    public String activatePlan(String selectedPlanType, String selectedUserType) {
        Map<String, Object> data = loadJsonData();

        List<Map<String, Object>> subscriptions = getSubscriptionsList(data);

        for (Map<String, Object> subscription : subscriptions) {
            String userType = (String) subscription.get("userType");
            String planType = (String) subscription.get("planType");
            if (userType.equals(selectedUserType) && planType.equals(selectedPlanType)) {
                subscription.put("status", "active");
                try {
                    JsonFileHandler.saveJsonData(data);
                } catch (IOException e) {
                    return "Error saving data: " + e.getMessage();
                }
                return "The " + selectedPlanType + " subscription plan for " + selectedUserType + " has been activated successfully.";
            }
        }
        return "Plan not found for the specified user type.";
    }


    public String getPlanStatus(String planType, String userType) {
        Map<String, Object> data = loadJsonData();
        if (data == null) return "Error: Data could not be loaded.";

        List<Map<String, Object>> subscriptions = getSubscriptionsList(data);
        if (subscriptions == null) return "Error: No subscriptions found.";

        for (Map<String, Object> subscription : subscriptions) {
            String userTypeFromData = (String) subscription.get("userType");
            String planTypeFromData = (String) subscription.get("planType");

            if (userTypeFromData != null && planTypeFromData != null && userTypeFromData.equalsIgnoreCase(userType) && planTypeFromData.equalsIgnoreCase(planType)) {

                Object status = subscription.get("status");
                if (status != null) {
                    return (String) status;
                } else {
                    return "Error: Status field is missing for the selected plan.";
                }
            }
        }
        return "Plan not found for the specified user type and plan type.";
    }
}



