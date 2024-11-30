package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstructorNotificationsAndUpdates {
    //Scenario: Notify clients about schedule changes
    @Given("the instructor updates a program schedule")
    public void the_instructor_updates_a_program_schedule() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the changes are saved")
    public void the_changes_are_saved() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system sends notifications to all enrolled clients about the updated schedule")
    public void the_system_sends_notifications_to_all_enrolled_clients_about_the_updated_schedule() {
        // Write code here that turns the phrase above into concrete actions
    }

    //end of Scenario: Notify clients about schedule changes


    //Scenario: Announce new programs or special offers
    @Given("the instructor has created or edited a program")
    public void the_instructor_has_created_or_edited_a_program() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor choose {string}")
    public void the_instructor_choose(String string) {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("specifies the target audience")
    public void specifies_the_target_audience() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system sends announcements to the selected clients or groups")
    public void the_system_sends_announcements_to_the_selected_clients_or_groups() {
        // Write code here that turns the phrase above into concrete actions
    }


    //end ofScenario: Announce new programs or special offers
}
