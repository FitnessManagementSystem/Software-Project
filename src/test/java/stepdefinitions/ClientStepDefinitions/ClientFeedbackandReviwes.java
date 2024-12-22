package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.ClientService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClientFeedbackandReviwes extends BaseSteps {
    private static final Logger logger = Logger.getLogger(ClientAccountManagement.class.getName());
    private final ClientService clientService = new ClientService();
    private String feedbackMessage;

    @Given("I am on the Feedback and Reviews page")
    public void i_am_on_the_feedback_and_reviews_page() {
        logger.info("Navigated to the feedback and reviews page");
        Map<String, Object> mockData = new HashMap<>();
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
        programs.add(Map.of(
                "id", 2,
                "title", "Flexibility",
                "duration", "6 weeks",
                "focus area", "Intermediate",
                "price", "$100",
                "status", "completed"
        ));
        mockData.put("programs", programs);

        //Mock the loadJsonData method to return mock data
        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> null);
    }


    @And("I have completed the program")
    public void iHaveCompletedTheProgram() {
        logger.info("User has completed the program");
    }

    @When("I want to rate and review the program {string} with rating {string} and review {string}")
    public void iWantToRateAndReviewTheProgramWithRatingAndReview(String programName, String rating, String review) {
        feedbackMessage = clientService.reviewProgram(programName, "Arqam", rating, review);
    }

    @Then("I should see a message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        Assert.assertEquals("Expected message didn't match the feedback message for submitting ar review", expectedMessage, feedbackMessage);
    }
}
