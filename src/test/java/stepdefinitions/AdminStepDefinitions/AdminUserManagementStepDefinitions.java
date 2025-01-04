package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.AdminService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class AdminUserManagementStepDefinitions {
    private static final Logger logger = Logger.getLogger(AdminUserManagementStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;

    @Given("I am logged in as an Admin user")
    public void iAmLoggedInAsAnAdminUser() {
    }

    @Given("I navigate to the User Management section")
    public void i_navigate_to_the_user_management_section() {
        logger.info("Navigated to the User Management section.");

    }

    @When("I {string} an account for {string} with name {string}, password {string}, and role {string}")
    public void i_an_account_for_with_name_password_and_role(String action, String userType, String name, String password, String role) {
        switch (action.toLowerCase()) {
            case "add":
                if (userType.equalsIgnoreCase("instructor")) {
                    feedbackMessage = adminService.addInstructor(name, password, role);
                    logger.info("Attempted to add instructor: " + feedbackMessage);
                } else if (userType.equalsIgnoreCase("client")) {
                    feedbackMessage = adminService.addClient(name, password, role);
                    logger.info("Attempted to add client: " + feedbackMessage);
                } else {
                    throw new IllegalArgumentException("Invalid user type: " + userType);
                }
                break;

            case "deactivate":
                if (userType.equalsIgnoreCase("instructor")) {
                    feedbackMessage = adminService.deactivateInstructor(name);
                    logger.info(feedbackMessage);
                } else if (userType.equalsIgnoreCase("client")) {
                    feedbackMessage = adminService.deactivateClient(name);
                    logger.info(feedbackMessage);
                } else {
                    throw new IllegalArgumentException("Invalid user type: " + userType);
                }
                break;

            case "approve":
                if (userType.equalsIgnoreCase("instructor")) {
                    feedbackMessage = adminService.approveInstructor(name);
                    logger.info(feedbackMessage);
                } else {
                    throw new IllegalArgumentException("Approve action is only allowed for instructors, not for " + userType);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @Then("I should see the account is {string} with message {string}")
    public void iShouldSeeTheAccountIsWithMessage(String expectedStatus, String expectedMessage) {
        // Assert the feedbackMessage matches the expected success message
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }
}