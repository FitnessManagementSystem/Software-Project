package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.Client;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class ClientFeedbackandReviwes {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final Client client = new Client();
    private String feedbackMessage;

    @Given("I am on the Feedback and Reviews page")
    public void i_am_on_the_feedback_and_reviews_page() {
        logger.info("Navigated to the feedback and reviews page");
    }


    @And("I have completed the program")
    public void iHaveCompletedTheProgram() {
        logger.info("User has completed the program");
    }

    @When("I want to rate and review the program {string} with rating {string} and review {string}")
    public void iWantToRateAndReviewTheProgramWithRatingAndReview(String programName, String rating, String review) {
        feedbackMessage = client.reviewProgram(programName, "Arqam", rating, review);
    }

    @Then("I should see a message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        Assert.assertEquals("Expected message didn't match the feedback message for submitting ar review", expectedMessage, feedbackMessage);
    }
}
