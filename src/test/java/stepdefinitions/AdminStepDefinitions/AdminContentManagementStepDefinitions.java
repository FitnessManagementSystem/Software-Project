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


public class AdminContentManagementStepDefinitions {
    //Approve or reject content

    private static final Logger logger = Logger.getLogger(AdminUserManagementStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;

    @When("I navigate to the Content Management section")
    public void iNavigateToTheContentManagementSection() {
        // Simulate navigating to the Content Management section (such as opening the admin page)
        logger.info("Navigated to the Content Management section.");
    }

    @And("I handle feedback from {string} to {string}")
    public void iHandleFeedbackFromTo(String instructorName, String clientName) {
        // Call the handleFeedback method to handle the feedback from the instructor to the client
        feedbackMessage = adminService.handleFeedback(instructorName, clientName);
        logger.info("Handled feedback from " + instructorName + " to " + clientName);
    }

    @Then("I should set status to {string} with the success message {string}")
    public void iShouldSeeStatusMarkedAsWithTheSuccessMessage(String expectedStatus, String expectedMessage) {
        // Retrieve the status from the feedback message returned by the service
        String feedbackStatus = feedbackMessage.contains("handled") ? "handled" : "not handled";

        // Assert that the feedback status matches the expected status
        Assert.assertEquals("The feedback status should be marked as handled.", expectedStatus, feedbackStatus);

        // Assert that the success message matches the expected message
        Assert.assertEquals("The success message should be correct.", expectedMessage, feedbackMessage);
    }

}


