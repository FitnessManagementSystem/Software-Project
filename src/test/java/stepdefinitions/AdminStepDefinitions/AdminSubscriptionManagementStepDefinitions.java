package stepdefinitions.AdminStepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdminSubscriptionManagementStepDefinitions {

    @When("I navigate to the Subscription Management section")
    public void iNavigateToTheSubscriptionManagementSection() {
    }

    @And("I {string} the {string} plan for {string}")
    public void iDoAction(String actionType, String subscriptionPlan, String userType) {
    }

    @Then("the plan status should be {string}")
    public void thePlanStatusShouldBe(String statusMessage) {
    }
}
