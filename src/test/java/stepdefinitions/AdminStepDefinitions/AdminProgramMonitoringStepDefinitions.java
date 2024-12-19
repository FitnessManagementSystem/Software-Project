package stepdefinitions.AdminStepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdminProgramMonitoringStepDefinitions {

    @When("I navigate to the Program Monitoring section")
    public void iNavigateToTheProgramMonitoringSection() {
    }

    //Generating reports
    @And("I view {string}")
    public void iView(String reportType) {
    }

    @Then("I should see {string} displayed")
    public void iShouldSeeDisplayed(String outcomeType) {
    }

    //Track program progress
    @And("I track program progress")
    public void iTrackProgramProgress() {
    }

    @Then("I should see a list of active and completed programs")
    public void iShouldSeeAListOfActiveAndCompletedPrograms() {
    }
}
