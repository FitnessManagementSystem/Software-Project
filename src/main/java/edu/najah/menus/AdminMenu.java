package edu.najah.menus;

import edu.najah.services.AdminService;

import java.util.Scanner;

import static edu.najah.app.Main.showMainMenu;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminService adminService = new AdminService();

    public static void displayAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Instructor                 2. Add Client");
            System.out.println("3. Deactivate Client              4. Deactivate Instructor");
            System.out.println("5. Approve Instructor             6. Handle Feedback");
            System.out.println("7. Get Active Programs Count      8. Get Completed Programs Count");
            System.out.println("9. Deactivate Plan                10. Activate Plan");
            System.out.println("11. Get Plan Status               12. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addInstructor();
                    break;
                case 2:
                    addClient();
                    break;
                case 3:
                    deactivateClient();
                    break;
                case 4:
                    deactivateInstructor();
                    break;
                case 5:
                    approveInstructor();
                    break;
                case 6:
                    handleFeedback();
                    break;
                case 7:
                    getActiveProgramsCount();
                    break;
                case 8:
                    getCompletedProgramsCount();
                    break;
                case 9:
                    deactivatePlan();
                    break;
                case 10:
                    activatePlan();
                    break;
                case 11:
                    getPlanStatus();
                    break;
                case 12:
                    showMainMenu();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addInstructor() {
        System.out.print("Enter instructor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        System.out.println(adminService.addInstructor(name, password, role));
    }

    private static void addClient() {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        System.out.println(adminService.addClient(name, password, role));
    }

    private static void deactivateClient() {
        System.out.print("Enter client name to deactivate: ");
        String name = scanner.nextLine();
        System.out.println(adminService.deactivateClient(name));
    }

    private static void deactivateInstructor() {
        System.out.print("Enter instructor name to deactivate: ");
        String name = scanner.nextLine();
        System.out.println(adminService.deactivateInstructor(name));
    }

    private static void approveInstructor() {
        System.out.print("Enter instructor name to approve: ");
        String name = scanner.nextLine();
        System.out.println(adminService.approveInstructor(name));
    }

    private static void handleFeedback() {
        System.out.print("Enter instructor name: ");
        String instructorName = scanner.nextLine();
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        System.out.println(adminService.handleFeedback(instructorName, clientName));
    }

    private static void getActiveProgramsCount() {
        int activeProgramsCount = adminService.getActiveProgramsCount();
        System.out.println("Active programs count: " + activeProgramsCount);
    }

    private static void getCompletedProgramsCount() {
        int completedProgramsCount = adminService.getCompletedProgramsCount();
        System.out.println("Completed programs count: " + completedProgramsCount);
    }

    private static void deactivatePlan() {
        System.out.print("Enter plan type to deactivate: ");
        String planType = scanner.nextLine();
        System.out.print("Enter user type for the plan: ");
        String userType = scanner.nextLine();
        System.out.println(adminService.deactivatePlan(planType, userType));
    }

    private static void activatePlan() {
        System.out.print("Enter plan type to activate: ");
        String planType = scanner.nextLine();
        System.out.print("Enter user type for the plan: ");
        String userType = scanner.nextLine();
        System.out.println(adminService.activatePlan(planType, userType));
    }

    private static void getPlanStatus() {
        System.out.print("Enter plan type: ");
        String planType = scanner.nextLine();
        System.out.print("Enter user type: ");
        String userType = scanner.nextLine();
        System.out.println(adminService.getPlanStatus(planType, userType));
    }
}
