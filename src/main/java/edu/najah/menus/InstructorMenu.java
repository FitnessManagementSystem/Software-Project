package edu.najah.menus;

import edu.najah.app.Main;
import edu.najah.services.InstructorService;

import java.util.Scanner;

public class InstructorMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InstructorService instructorService = new InstructorService();

    public static void displayInstructorMenu() {
        while (true) {
            System.out.println("\nInstructor Menu:");
            System.out.println("1. Create Program");
            System.out.println("2. Delete Program");
            System.out.println("3. Send Report");
            System.out.println("4. Send Message");
            System.out.println("5. Send Feedback");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    createProgram();
                    break;
                case 2:
                    deleteProgram();
                    break;
                case 3:
                    sendReport();
                    break;
                case 4:
                    sendMessage();
                    break;
                case 5:
                    sendFeedback();
                    break;
                case 6:
                    System.out.println("Exiting the instructor menu. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle creating a program
    private static void createProgram() {
        System.out.print("Enter program title: ");
        String title = scanner.nextLine();

        System.out.print("Enter program duration: ");
        String duration = scanner.nextLine();

        System.out.print("Enter program difficulty level: ");
        String difficulty = scanner.nextLine();

        System.out.print("Enter program price: ");
        String price = scanner.nextLine();

        String result = instructorService.createProgram(title, duration, difficulty, price);
        System.out.println(result);
    }

    // Handle deleting a program
    private static void deleteProgram() {
        System.out.print("Enter the title of the program to delete: ");
        String title = scanner.nextLine();

        String result = instructorService.deleteProgram(title);
        System.out.println(result);
    }

    // Handle sending a report
    private static void sendReport() {
        System.out.print("Enter report content: ");
        String reportContent = scanner.nextLine();

        System.out.print("Enter receiver's name: ");
        String receiver = scanner.nextLine();

        String result = instructorService.sendReport(reportContent, receiver, Main.getUserName());
        System.out.println(result);
    }

    // Handle sending a message
    private static void sendMessage() {
        System.out.print("Enter message content: ");
        String messageContent = scanner.nextLine();

        System.out.print("Enter receiver's name: ");
        String receiver = scanner.nextLine();

        String result = instructorService.sendMessage(messageContent, receiver, Main.getUserName());
        System.out.println(result);
    }

    // Handle sending feedback
    private static void sendFeedback() {
        System.out.print("Enter feedback content: ");
        String feedbackContent = scanner.nextLine();

        System.out.print("Enter receiver's name: ");
        String receiver = scanner.nextLine();

        String result = instructorService.sendFeedback(feedbackContent, receiver, Main.getUserName());
        System.out.println(result);
    }
}
