package stepdefinitions.ClientStepDefinitions;

import edu.najah.Services.Client;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class ClientAccountManagment {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final Client client = new Client();
    private String feedbackMessage;


    @Given("I am on the account management page")
    public void i_am_on_the_account_management_page() {
        logger.info("Navigated to the account management page.");
    }

    @When("I enter the personal detail {string} with value {string}")
    public void i_enter_the_personal_detail_with_value(String detailType, String value) {
        client.setName(value);
        logger.info("Entered personal detail: " + detailType + " with value: " + value);
    }

    @When("I set the dietary preference {string} to {string}")
    public void i_set_the_dietary_preference_to(String preferenceType, String value) {
        client.setDietaryPreference(value);
        logger.info("Set dietary preference: " + preferenceType + " to value: " + value);
    }

    @When("I add the dietary restriction {string} to {string}")
    public void i_add_the_dietary_restriction_to(String restrictionType, String value) {
        client.setDietaryRestrictions(value);
        logger.info("Added dietary restriction: " + restrictionType + " with value: " + value);
    }

    @And("I set the fitness goal to {string}")
    public void iSetTheFitnessGoalTo(String goal) {
        client.setFitnessGoal(goal);
        logger.info("Set fitness goal to: " + goal);
    }

    @And("I set the age to {string}")
    public void iSetTheAgeTo(String age) {
        try {
            client.setAge(Integer.parseInt(age));
            logger.info("Set age to: " + age);
        } catch (NumberFormatException e) {
            logger.severe("Invalid age value provided: " + age);
            throw new IllegalArgumentException("Age must be a valid number", e);
        }
    }

    @When("I save the profile")
    public void i_save_the_profile() {
        feedbackMessage = client.createUserProfile(
                client.getName(),
                client.getDietaryPreference(),
                client.getDietaryRestrictions(),
                client.getAge(),
                client.getFitnessGoal()
        );
        logger.info("Attempted to save profile: " + feedbackMessage);
    }

    @Then("I should see {string}")
    public void i_should_see(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }
}
