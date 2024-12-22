package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.Client;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.logging.Logger;

public class ClientProgressTracking {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final Client client = new Client();
    private String feedbackMessage;

    @Given("I am on the Progress Tracking page")
    public void i_am_on_the_progress_tracking_page() {
        client.setName("instructor1");
        logger.info("Navigated to the personal programs page");
    }

    @Then("I should see a list of the programs I have completed, with the badge {string} and message {string}")
    public void iShouldSeeAListOfTheProgramsIHaveCompletedWithTheBadgeAndMessage(String programName, String expectedMessage) {
        feedbackMessage = client.generateCompletedProgramsList(programName);
        Assert.assertEquals(feedbackMessage, expectedMessage);
    }
}
