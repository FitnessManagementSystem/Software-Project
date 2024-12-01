package stepdefinitions;

import java.util.Scanner;

import io.cucumber.java.en.Given;

public class InstructorCommonStepDefinitions {

    private boolean isLoggedIn = false;

    @Given("the instructor is logged into the system")
    public void the_instructor_is_logged_into_the_system() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Instructor Portal. Please log in.");

        // Simulated login process
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Dummy credentials for validation
        String validUsername = "instructor";
        String validPassword = "123";

        if (username.equals(validUsername) && password.equals(validPassword)) {
            isLoggedIn = true;
            System.out.println("Login successful! Instructor is now logged in.");
        } else {
            isLoggedIn = false;
            System.out.println("Login failed! Please check your username or password.");
        }
    }
}
