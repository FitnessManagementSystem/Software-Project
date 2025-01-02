package stepdefinitions.InstructorStepDefinitions;

import edu.najah.services.InstructorService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.logging.Logger;

public class InstructorClientInteraction {
    private static final Logger logger = Logger.getLogger(InstructorClientInteraction.class.getName());
    private final InstructorService instructorService = new InstructorService();
    private String feedbackMessage;
    private String feedbackStatus;

    @Given("the instructor accesses the client interaction page")
    public void the_instructor_accesses_the_client_interaction_page() {
        // Simulate the action of accessing the page
        logger.info("The instructor accesses the client interaction page");
    }

    @When("the instructor sends a report {string} to the client")
    public void theInstructorSendsAReportToTheClient (String report) throws IOException {
        feedbackMessage = instructorService.sendReport (report);

    }

    @When("the instructor sends a message {string} to the client")
    public void theInstructorSendsAMessageToTheClient (String message) throws IOException{
        feedbackMessage = instructorService.sendMessage(message);

    }


    @Then("I should see a report {string}")
    public void iShouldSeeAReport(String reportMessage) throws IOException {
        Assert.assertEquals("Feedback message did not match expected", reportMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + reportMessage);
    }

    @Then("I should see a message  {string}")
    public void iShouldSeeAMessage(String message) {
        Assert.assertEquals("Feedback message did not match expected", message, feedbackMessage);
        logger.info("Feedback message matched expected: " + message);
    }


    @When("the instructor sends a feedback {string} to the client")
    public void theInstructorSendsAFeedbackToTheClient(String feedback) throws IOException {
        feedbackMessage = instructorService.sendFeedback(feedback);

    }

    @Then("I should see a feedback {string}")
    public void iShouldSeeAFeedback(String feedback) {
        Assert.assertEquals("Feedback message did not match expected", feedback, feedbackMessage);
        logger.info("Feedback message matched expected: " + feedback);
    }
}

