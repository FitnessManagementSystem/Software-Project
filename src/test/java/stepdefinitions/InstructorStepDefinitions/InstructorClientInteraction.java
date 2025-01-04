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
    @Given("the instructor accesses the client interaction page")
    public void the_instructor_accesses_the_client_interaction_page() {
        // Simulate the action of accessing the page
        logger.info("The instructor accesses the client interaction page");
    }


    @When("the instructor sends a report {string}  from {string}to the {string}")
    public void theInstructorSendsAReportFromToThe(String report, String reciver, String sender) {
        feedbackMessage = instructorService.sendReport (report,reciver,sender);

    }
    @Then("I should see a report {string}")
    public void iShouldSeeAReport(String reportMessage) throws IOException {
        Assert.assertEquals("Feedback message did not match expected", reportMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + reportMessage);
    }


    @When("the instructor sends a message {string}  from {string}to the {string}")
    public void theInstructorSendsAMessageFromToThe(String message, String reciver, String sender) {
        feedbackMessage = instructorService.sendMessage (message,reciver,sender);

    }

    @Then("I should see a message  {string}")
    public void iShouldSeeAMessage(String MSG) {
        Assert.assertEquals("Feedback message did not match expected",MSG , feedbackMessage);
        logger.info("Feedback message matched expected: " + MSG);
    }

    @When("the instructor sends a feedback {string}  from {string}to the {string}")
    public void theInstructorSendsAFeedbackFromToThe(String feedback, String reciver, String sender) {
        feedbackMessage = instructorService.sendFeedback (feedback,reciver,sender);

    }

    @Then("I should see a feedback {string}")
    public void iShouldSeeAFeedback(String feedbackM) {
        Assert.assertEquals("Feedback message did not match expected",feedbackM , feedbackMessage);
        logger.info("Feedback message matched expected: " + feedbackM);
    }
}

