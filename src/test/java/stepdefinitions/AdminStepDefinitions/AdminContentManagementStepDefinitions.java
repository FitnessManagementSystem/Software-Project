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


public class AdminContentManagementStepDefinitions extends BaseSteps {

    private static final Logger logger = Logger.getLogger(AdminUserManagementStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;

    @Given("I am logged in")
    public void iAmLoggedIn() {
        Map<String, Object> mockData = new HashMap<>();
        List<Map<String, String>> feedback = new ArrayList<>();
        Map<String, String> feedbackItem = new HashMap<>();
        feedbackItem.put("from", "Instructor1");
        feedbackItem.put("to", "Client1");
        feedbackItem.put("message", "Great work!");
        feedbackItem.put("status", "not handled");
        feedback.add(feedbackItem);

        List<Map<String, String>> handledFeedbacks = new ArrayList<>();

        mockData.put("feedback", feedback);
        mockData.put("handledFeedbacks", handledFeedbacks);

        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> null);
    }

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
        Assert.assertEquals("The success message should be correct.", expectedMessage, feedbackMessage);
    }
}


