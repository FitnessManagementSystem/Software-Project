package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.AdminService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import stepdefinitions.ClientStepDefinitions.BaseSteps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AdminSubscriptionManagementStepDefinitions extends BaseSteps {
    private static final Logger logger = Logger.getLogger(AdminSubscriptionManagementStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;
    private String selectedPlanType;
    private String selectedUserType;

    @Given("I navigate to the Subscription management section")
    public void iNavigateToTheSubscriptionManagementSection() {
        logger.info("Navigated to the Subscription Management section.");
        Map<String, Object> mockData = new HashMap<>();
        List<Map<String, Object>> subscriptions = new ArrayList<>();

        subscriptions.add(new HashMap<String, Object>() {{
            put("userType", "Instructor1");
            put("planType", "Basic");
            put("status", "active");
        }});
        subscriptions.add(new HashMap<String, Object>() {{
            put("userType", "Instructor2");
            put("planType", "Premium");
            put("status", "inactive");
        }});
        subscriptions.add(new HashMap<String, Object>() {{
            put("userType", "Client1");
            put("planType", "Basic");
            put("status", "active");
        }});
        subscriptions.add(new HashMap<String, Object>() {{
            put("userType", "Client2");
            put("planType", "Premium");
            put("status", "inactive");
        }});
        mockData.put("subscriptions", subscriptions);

        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> null);
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
