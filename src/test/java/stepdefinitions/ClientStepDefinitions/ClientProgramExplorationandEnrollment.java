package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.Client;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class ClientProgramExplorationandEnrollment {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final Client client = new Client();
    boolean isWorking = Boolean.FALSE;
    private String feedbackMessage;
    private String filterType;

    @Given("I am on the Program Exploration and Enrollment page")
    public void i_am_on_the_program_exploration_and_enrollment_page() {
        logger.info("Navigated to the program exploration page.");
    }

    @When("I select the {string} filter")
    public void i_select_the_filter(String string) {
        filterType = string;
    }

    @And("I press on search")
    public void iPressOnSearch() {
        if (client.searchProgramsByFilter(filterType).isEmpty()) {
            isWorking = Boolean.FALSE;
        } else {
            isWorking = Boolean.TRUE;
        }
    }

    @Then("I should see a list of programs such as {string} based on {string}")
    public void iShouldSeeAListOfProgramsSuchAsBasedOn(String filterValue, String filterType) {
        if (isWorking) {
            assert (true);
        } else {
            assert (false);
        }
    }

    @When("I confirm my enrollment to a program {string} by {string} and status {string}")
    public void iConfirmMyEnrollmentToAProgramByAndStatus(String programName, String userName, String status) {
        feedbackMessage = client.enrollInProgram(programName, userName);
    }


    @Then("I should see the message {string}")
    public void i_should_see_the_message(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);

    }


}