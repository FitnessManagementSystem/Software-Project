package stepdefinitions.AdminStepDefinitions;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import edu.najah.Services.Admin;

public class AdminContentManagementStepDefinitions {
    //Approve or reject content
    @When("I navigate to the Content Management section")
    public void iNavigateToTheContentManagementSection() {
    }

    @And("I {string} a {string}")
    public void iApproveOrDeny(String actionType, String contentType) {
    }

    @Then("the content should be {string}")
    public void theContentShouldBe(String statusMessage) {
    }

    //Handle user feedback and complaints
    @And("I handle user complaints")
    public void iHandleUserComplaints() {
    }

    @Then("I should see the complaints are marked as resolved")
    public void iShouldSeeTheComplaintsAreMarkedAsResolved() {
    }
}
