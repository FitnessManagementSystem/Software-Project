package stepdefinitions.InstructorStepDefinitions;

import edu.najah.services.InstructorService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class InstructorProgramManagement {
    private static final Logger logger = Logger.getLogger(InstructorProgramManagement.class.getName());
    private final InstructorService instructorService = new InstructorService();
    private String feedbackMessage;

    @When("I enter the Program_title {string} to {string}")
    public void i_enter_the_program_title_to(String Program_title, String Program_title_Value) {
        instructorService.setTitle(Program_title_Value);
        logger.info("Program_Title: " + Program_title + " with value: " + Program_title_Value);
    }

    @When("I set the Duration {string} to {string}")
    public void i_set_the_duration_to(String Duration, String Duration_Value) {
        instructorService.setDuration(Duration_Value);
        logger.info("Duration: " + Duration + " with value: " + Duration_Value);
    }

    @When("I add the Difficulty level {string} to {string}")
    public void i_add_the_difficulty_level_to(String difficulty_level, String difficulty_level_value) {
        instructorService.setDifficaltyLevel(difficulty_level_value);
        logger.info("difficulty_level: " + difficulty_level + " with value: " + difficulty_level_value);
    }

    @When("I set the Price {int} to {string}")
    public void i_set_the_price_to(Integer Price_Value, String Price) {
        instructorService.setPrice(Price_Value);
        logger.info("Price: " + Price + " Price value: " + Price_Value);
    }

    @When("I save the profile")
    public void i_save_the_profile() throws Exception {
        feedbackMessage = instructorService.createProgram(instructorService.getTitle(), instructorService.getDuration(), instructorService.getDifficalty(), String.valueOf(instructorService.getPrice()));
        logger.info("Attempted to save program: " + feedbackMessage);
    }


    @And("I delete the program")
    public void iDeleteTheProgram()throws Exception {
        feedbackMessage = instructorService.deleteProgram(instructorService.getTitle());
        logger.info("Attempted to delete program: " + feedbackMessage);
    }

    @Then("I should see {string} for deleting program.")
    public void iShouldSeeForDeletingProgram(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }

    @Then("I should see {string} and announce the clients about it")
    public void iShouldSeeAndAnnounceTheClientsAboutIt(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }

}
