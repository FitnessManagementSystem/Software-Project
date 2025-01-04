package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.AdminService;
import io.cucumber.java.en.And;
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
        logger.info("Navigated to the Content Management section.");
    }

    @And("I handle feedback from {string} to {string}")
    public void iHandleFeedbackFromTo(String instructorName, String clientName) {
        feedbackMessage = adminService.handleFeedback(instructorName, clientName);
        logger.info("Handled feedback from " + instructorName + " to " + clientName);
    }

    @Then("I should set status to {string} with the success message {string}")
    public void iShouldSeeStatusMarkedAsWithTheSuccessMessage(String expectedStatus, String expectedMessage) {
        String feedbackStatus = feedbackMessage.contains("handled") ? "handled" : "not handled";
        Assert.assertEquals("The feedback status should be marked as handled.", expectedStatus, feedbackStatus);
        Assert.assertEquals("The success message should be correct.", expectedMessage, feedbackMessage);
    }
}


