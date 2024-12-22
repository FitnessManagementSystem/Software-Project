package stepdefinitions.AdminStepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdminUserManagementStepDefinitions {

    //Manage user accounts
    @When("I navigate to the User Management section")
    public void iNavigateToTheUserManagementSection() {

    }

    @And("I {string} an account for {string}")
    public void iAnAccountFor(String action, String userType) {

    }

    @Then("I should see the account is {string}")
    public void iShouldSeeTheAccountIs(String outcomeMessage) {

    }


}
