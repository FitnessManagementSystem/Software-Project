package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.ClientService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClientProgressTracking extends BaseSteps {
    private static final Logger logger = Logger.getLogger(ClientAccountManagement.class.getName());
    private final ClientService clientService = new ClientService();
    private List<Map<String, Object>> feedbackMessage;


    @Given("I am on the Progress Tracking page")
    public void i_am_on_the_progress_tracking_page() {
        logger.info("Navigated to the personal programs page");

        Map<String, Object> mockData = new HashMap<>();
        List<Map<String, Object>> enrolledUsers = new ArrayList<>();
        List<Map<String, Object>> programs = new ArrayList<>();

        programs.add(Map.of(
                "id", 1,
                "title", "Weight Loss",
                "duration", "4 weeks",
                "difficulty", "Beginner",
                "price", "$100",
                "status", "active"
        ));
        programs.add(Map.of(
                "id", 4,
                "title", "Yoga",
                "duration", "4 weeks",
                "focus area", "Advanced",
                "price", "$120",
                "status", "completed"
        ));
        enrolledUsers.add(Map.of(
                "userName", "Arqam",
                "programId", 4
        ));
        mockData.put("enrolledUsers", enrolledUsers);
        mockData.put("programs", programs);

        //Mock the loadJsonData method to return mock data
        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
    }

    @When("I want to see my completed programs list")
    public void iWantToSeeMyCompletedProgramsList() {
        feedbackMessage = clientService.getCompletedProgramsList("Arqam");
    }

    @Then("I should see a list of the programs I have completed, with the program name")
    public void iShouldSeeAListOfTheProgramsIHaveCompletedWithTheProgramName() {
        Assert.assertEquals(1, feedbackMessage.size());
    }


}
