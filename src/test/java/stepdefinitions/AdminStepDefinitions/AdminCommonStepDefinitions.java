package stepdefinitions.AdminStepDefinitions;

import io.cucumber.java.en.Given;

public class AdminCommonStepDefinitions {
    @Given("I am logged in as an Admin user")
    public void iAmLoggedInAsAnAdminUser() {
        String role = "Admin";
    }
}
