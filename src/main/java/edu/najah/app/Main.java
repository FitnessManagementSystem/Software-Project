package edu.najah.app;

import edu.najah.utilities.JsonFileHandler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.najah.menus.AdminMenu.displayAdminMenu;
import static edu.najah.menus.ClientMenu.displayClientMenu;
import static edu.najah.menus.ClientMenu.displayClientMenu;
import static edu.najah.menus.InstructorMenu.displayInstructorMenu;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private  static String UserName = "";
    public static void main(String[] args) {
        // Show the main menu and handle login/signup choices
        showMainMenu();
    }
    public  static String getUserName(){
        return UserName;
    }
    // Method to display the main menu
    public static void showMainMenu() {
        while (true) {
            System.out.println("\nWelcome! Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleSignup();
                    break;
                case 3:
                    System.out.println("Thank you for using our system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle login functionality
    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Map<String, Map<String, String>> users;
        try {
            users = (Map<String, Map<String, String>>) JsonFileHandler.loadJsonData().get("users");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading user data", e);
            System.out.println("Error loading user data. Please try again later.");
            return;
        }

        if (users != null && users.containsKey(username)) {
            Map<String, String> userDetails = users.get(username);
            if (userDetails.get("password").equals(password)) {
                String role = userDetails.get("role");
                System.out.println("Login successful! Welcome, " + username + "!");
                UserName=username;
                // Show the appropriate menu based on the role
                showRoleMenu(role);
                return;
            }
        }

        System.out.println("Invalid username or password. Please try again.");
    }

    // Handle signup functionality
    private static void handleSignup() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Prompt user for role (client or instructor)
        System.out.print("Enter role (client/instructor): ");
        String role = scanner.nextLine().toLowerCase();

        // Validate role
        if (!role.equals("client") && !role.equals("instructor")) {
            System.out.println("Invalid role. Please try again.");
            return;
        }

        try {
            Map<String, Object> data = JsonFileHandler.loadJsonData();
            if (data == null) {
                data = new HashMap<>();
            }

            Object usersObject = data.get("users");
            if (!(usersObject instanceof Map)) {
                System.out.println("Invalid data structure for users. Resetting to empty.");
                usersObject = new HashMap<String, Map<String, String>>();
                data.put("users", usersObject);
            }
            Map<String, Map<String, String>> users = (Map<String, Map<String, String>>) usersObject;

            if (users.containsKey(username)) {
                System.out.println("Username already exists. Please try again.");
                return;
            }

            if (role.equals("instructor")) {
                handleInstructorSignup(data, username, password);
            } else {
                handleClientSignup(users, username, password, data);
            }
        } catch (ClassCastException e) {
            LOGGER.log(Level.SEVERE, "Data type mismatch. Check the JSON structure.", e);
            System.out.println("Error during signup. Please try again later.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error during signup", e);
            System.out.println("Error during signup. Please try again later.");
        }
    }
    private static void handleInstructorSignup(Map<String, Object> data, String username, String password) throws Exception {
        // Load the list of instructors awaiting approval
        List<Map<String, String>> awaitingApproval = (List<Map<String, String>>) data.get("instructorsAwaitingApproval");

        // If no list is found, initialize a new one
        if (awaitingApproval == null) {
            awaitingApproval = new ArrayList<>();
            data.put("instructorsAwaitingApproval", awaitingApproval);
        }

        // Check if the username already exists in the awaitingApproval list
        for (Map<String, String> instructor : awaitingApproval) {
            if (instructor.containsKey("username") && instructor.get("username").equals(username)) {
                System.out.println("An instructor application with this username already exists. Please try again.");
                return;
            }
        }

        // Create new instructor entry
        Map<String, String> newInstructor = new HashMap<>();
        newInstructor.put("username", username);
        newInstructor.put("password", password);

        // Add the new instructor to the awaitingApproval list
        awaitingApproval.add(newInstructor);

        // Save the updated data back to the file
        JsonFileHandler.saveJsonData(data);

        System.out.println("Thank you for applying as an Instructor. Please wait for admin approval.");
    }


    private static void handleClientSignup(Map<String, Map<String, String>> users, String username, String password, Map<String, Object> data) throws Exception {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("password", password);
        userDetails.put("role", "Client");
        users.put(username, userDetails);

        JsonFileHandler.saveJsonData(data);
        System.out.println("Sign up successful! You can now log in.");
    }

    // Method to show menu for a specific role
    private static void showRoleMenu(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                displayAdminMenu();
                break;
            case "instructor":
                displayInstructorMenu();
                break;
            case "client":
                displayClientMenu();
                break;
            default:
                System.out.println("Unknown role. Please contact support.");
        }
    }
}
