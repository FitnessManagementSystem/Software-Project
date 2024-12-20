package stepdefinitions.AdminStepDefinitions;

import edu.najah.Services.Admin;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stepdefinitions.ClientStepDefinitions.ClientAccountManagment;

import java.util.logging.Logger;

public class AdminUserManagementStepDefinitions {
    private static final Logger logger = Logger.getLogger(ClientAccountManagment.class.getName());
    private final Admin admin = new Admin();
    private String feedbackMessage;

    @When("I navigate to the User Management section")
    public void i_navigate_to_the_user_management_section() {
        logger.info("Navigated to the User Management section.");
    }

    @When("I {string} an account for {string}")
    public void i_an_account_for(String Action, String User_Type) {

    }

    @Then("I should see the account is {string}")
    public void i_should_see_the_account_is(String string) {
        // Write code here that turns the phrase above into concrete actions
    }


}
