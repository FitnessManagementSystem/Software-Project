package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstructorProgramManagement {
    //Scenario: Create a fitness program
    @Given("the instructor is logged into the system")
    public void the_instructor_is_logged_into_the_system() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor selects {string}")
    public void the_instructor_selects(String string) {
        // Write code here that turns the phrase above into concrete actions

    }

    @When("enters details like title, duration, difficulty level, and goals")
    public void enters_details_like_title_duration_difficulty_level_and_goals() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("uploads materials such as video tutorials, images, or documents")
    public void uploads_materials_such_as_video_tutorials_images_or_documents() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("sets a price and schedule for the program")
    public void sets_a_price_and_schedule_for_the_program() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system saves the program and displays a confirmation message")
    public void the_system_saves_the_program_and_displays_a_confirmation_message() {
        // Write code here that turns the phrase above into concrete actions

    }
    //end of Scenario: Create a fitness program


    //Scenario: Update an existing fitness program
    @Given("the instructor is logged into the system and viewing their program dashboard")
    public void the_instructor_is_logged_into_the_system_and_viewing_their_program_dashboard() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor selects a program to edit")
    public void the_instructor_selects_a_program_to_edit() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("modifies details such as title, duration, or schedule")
    public void modifies_details_such_as_title_duration_or_schedule() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("confirms the changes")
    public void confirms_the_changes() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system updates the program and notifies enrolled clients")
    public void the_system_updates_the_program_and_notifies_enrolled_clients() {
        // Write code here that turns the phrase above into concrete actions
    }
    //end of Scenario: Update an existing fitness program


    //Scenario: Delete a fitness program

    @Given("the instructor has accessed the list of their active programs")
    public void the_instructor_has_accessed_the_list_of_their_active_programs() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the instructor selects a program to delete")
    public void the_instructor_selects_a_program_to_delete() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("confirms the deletion")
    public void confirms_the_deletion() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system removes the program and notifies enrolled clients about its cancellation")
    public void the_system_removes_the_program_and_notifies_enrolled_clients_about_its_cancellation() {
        // Write code here that turns the phrase above into concrete actions
    }
    //end of Scenario: Delete a fitness program
}
