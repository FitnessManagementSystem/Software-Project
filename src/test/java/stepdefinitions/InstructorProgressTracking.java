package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstructorProgressTracking {
    //Scenario: View client progress
    @Given("the instructor is viewing their list of enrolled clients")
    public void the_instructor_is_viewing_their_list_of_enrolled_clients() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor selects a client")
    public void the_instructor_selects_a_client() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system displays the client’s attendance, completion rate, and performance metrics")
    public void the_system_displays_the_client_s_attendance_completion_rate_and_performance_metrics() {
        // Write code here that turns the phrase above into concrete actions
    }

    //end ofScenario: View client progress

    //Scenario: Send motivational reminders or recommendations
    @Given("the instructor is viewing a client’s profile")
    public void the_instructor_is_viewing_a_client_s_profile() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor click {string}")
    public void the_instructor_click(String string) {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("enters a motivational message or recommendation")
    public void enters_a_motivational_message_or_recommendation() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system sends the notification to the client")
    public void the_system_sends_the_notification_to_the_client() {
        // Write code here that turns the phrase above into concrete actions

    }
    //end ofScenario: Send motivational reminders or recommendations
}
