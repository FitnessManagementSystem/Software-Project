package stepdefinitions.AdminStepDefinitions;


import edu.najah.services.AdminService;
import edu.najah.services.ClientService;
import edu.najah.services.InstructorService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class AdminProgramMonitoringStepDefinitions {
    private static final Logger logger = Logger.getLogger(AdminUserManagementStepDefinitions.class.getName());
    AdminService adminservice = new AdminService();
    private String feedbackMessage;

    @When("I navigate to the Program Monitoring section")
    public void iNavigateToTheProgramMonitoringSection() {
        logger.info("Navigated to the Monitoring section.");
        // In a real application, add code to navigate the user to the section.
    }

    @Then("I should see the report displayed")
    public void iShouldSeeDisplayed() {
        // Call the admin service to generate the report and capture the feedback message
        feedbackMessage = adminservice.generatePopularProgramsReport();
        logger.info("Generated report feedback: " + feedbackMessage);
    }

    @And("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String expectedMessage) {
        // Assert the feedbackMessage matches the expected success message
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }


}
