package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.ClientService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.mockito.Mockito.mockStatic;

public class ClientAccountManagment {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final ClientService clientService = new ClientService();
    private String feedbackMessage;

    private MockedStatic<JsonFileHandler> mockedFileHandler;

    @Given("I am on the account management page")
    public void INavigateToAccountManagementPage() {
        logger.info("Navigated to the account management page.");
        mockedFileHandler = mockStatic(JsonFileHandler.class);

        Map<String, Object> mockData = new HashMap<>();
        Map<String, Map<String, String>> userProfiles = new HashMap<>();

        userProfiles.put("Jack", Map.of("dietaryPreference", "Yes", "dietaryRestriction", "Yes", "age", "20", "fitnessGoal", "Weight Loss"));

        mockData.put("userProfiles", userProfiles);

        //Mock the loadJsonData method to return mock data
        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> null);
    }

    @When("I enter the name to {string}, dietary preference to {string}, dietary restriction to {string}, the age to {string}, the fitness goal to {string} and save")
    public void IEnterMyDetailsAndSave(String name, String dietaryPreference, String dietaryRestriction, String age, String fitnessGoal) {
        feedbackMessage = clientService.createUserProfile(name, dietaryPreference, dietaryRestriction, Integer.parseInt(age), fitnessGoal);
        logger.info("Attempted to save profile: " + feedbackMessage);

    }

    @Then("I should see {string}")
    public void i_should_see(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
        mockedFileHandler.close();
    }
}
