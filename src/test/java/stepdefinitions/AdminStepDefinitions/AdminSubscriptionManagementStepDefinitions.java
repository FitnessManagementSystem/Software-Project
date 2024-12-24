package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.AdminService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class AdminSubscriptionManagementStepDefinitions {
    private static final Logger logger = Logger.getLogger(AdminSubscriptionManagementStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;
    private String selectedPlanType;
    private String selectedUserType;

    @When("I navigate to the Subscription Management section")
    public void iNavigateToTheSubscriptionManagementSection() {
        logger.info("Navigated to the Subscription Management section.");
    }

    @And("I have selected the {string} subscription plan for {string}")
    public void iHaveSelectedTheSubscriptionPlanFor(String planType, String userType) {
        this.selectedPlanType = planType;
        this.selectedUserType = userType;
        logger.info("Selected " + planType + " subscription plan for " + userType + ".");
    }

    @When("I {string} the plan")
    public void iThePlan(String action) {
        switch (action.toLowerCase()) {
            case "deactivate":
                feedbackMessage = adminService.deactivatePlan(selectedPlanType, selectedUserType);
                logger.info("Deactivated " + selectedPlanType + " plan for " + selectedUserType + ": " + feedbackMessage);
                break;
            case "activate":
                feedbackMessage = adminService.activatePlan(selectedPlanType, selectedUserType);
                logger.info("Activated " + selectedPlanType + " plan for " + selectedUserType + ": " + feedbackMessage);
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @Then("The {string} subscription plan for {string} should be marked as {string}")
    public void theSubscriptionPlanForShouldBeMarkedAs(String planType, String userType, String expectedStatus) {
        String actualStatus = adminService.getPlanStatus(planType, userType);
        Assert.assertEquals("The plan status did not match the expected status.", expectedStatus.toLowerCase(), actualStatus.toLowerCase());
        logger.info("Verified that the " + planType + " subscription plan for " + userType + " is marked as " + expectedStatus + ".");
    }

    @And("I should see a success message: {string}")
    public void iShouldSeeASuccessMessage(String expectedMessage) {
        Assert.assertEquals("The feedback message did not match the expected message.", expectedMessage, feedbackMessage);
        logger.info("Verified success message: " + expectedMessage);
    }
}

