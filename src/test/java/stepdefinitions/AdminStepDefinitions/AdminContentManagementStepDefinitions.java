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

    @And("I {string} a {string}")
    public void iApproveOrDeny(String actionType, String contentType) {
        // Call the approveContent method from adminService to approve or reject the content
        feedbackMessage = adminService.approveContent(contentType, actionType);  // The feedback message is returned from the service
        logger.info("Performed action: " + actionType + " on content: " + contentType);
    }

    @Then("the content should be {string}")
    public void theContentShouldBe(String expectedMessage) {
        // Validate that the feedback message matches the expected message
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        // Log the outcome for visibility
        logger.info("Feedback message matched expected: " + expectedMessage);
    }


}
