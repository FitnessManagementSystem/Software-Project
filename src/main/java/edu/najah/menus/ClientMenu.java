package edu.najah.menus;

import edu.najah.services.ClientService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static edu.najah.app.Main.showMainMenu;

public class ClientMenu {

    public static void displayClientMenu() {
        ClientService clientService = new ClientService();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n====== Client Menu ======");
            System.out.println("1. Create User Profile");
            System.out.println("2. Search Programs by Filter");
            System.out.println("3. Enroll in a Program");
            System.out.println("4. Get Completed Programs List");
            System.out.println("5. Review a Program");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createUserProfile(clientService, scanner);
                case 2 -> searchProgramsByFilter(clientService, scanner);
                case 3 -> enrollInProgram(clientService, scanner);
                case 4 -> getCompletedProgramsList(clientService, scanner);
                case 5 -> reviewProgram(clientService, scanner);
                case 6 -> {
                    showMainMenu();
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void createUserProfile(ClientService clientService, Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter dietary preference: ");
        String dietaryPreference = scanner.nextLine();
        System.out.print("Enter dietary restriction: ");
        String dietaryRestriction = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter fitness goal: ");
        String fitnessGoal = scanner.nextLine();

        String result = clientService.createUserProfile(name, dietaryPreference, dietaryRestriction, age, fitnessGoal);
        System.out.println(result);
    }

    private static void searchProgramsByFilter(ClientService clientService, Scanner scanner) {
        System.out.print("Enter filter type: ");
        String filterType = scanner.nextLine();

        List<Map<String, Object>> programs = clientService.searchProgramsByFilter(filterType);
        if (programs.isEmpty()) {
            System.out.println("No programs found for the specified filter.");
        } else {
            System.out.println("Programs matching the filter:");
            for (Map<String, Object> program : programs) {
                System.out.println(program);
            }
        }
    }

    private static void enrollInProgram(ClientService clientService, Scanner scanner) {
        System.out.print("Enter program name: ");
        String programName = scanner.nextLine();
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();

        String result = clientService.enrollInProgram(programName, userName);
        System.out.println(result);
    }

    private static void getCompletedProgramsList(ClientService clientService, Scanner scanner) {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();

        List<Map<String, Object>> programs = clientService.getCompletedProgramsList(userName);
        if (programs.isEmpty()) {
            System.out.println("No completed programs found for the user.");
        } else {
            System.out.println("Completed programs:");
            for (Map<String, Object> program : programs) {
                System.out.println(program);
            }
        }
    }

    private static void reviewProgram(ClientService clientService, Scanner scanner) {
        System.out.print("Enter program name: ");
        String programName = scanner.nextLine();
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter rating (1-10): ");
        String rating = scanner.nextLine();
        System.out.print("Enter review: ");
        String review = scanner.nextLine();

        String result = clientService.reviewProgram(programName, userName, rating, review);
        System.out.println(result);
    }
}
