package stepdefinitions.InstructorStepDefinitions;

import io.cucumber.java.en.Given;

import java.util.logging.Logger;

public class InstructorCommonStepDefinitions {
    private static final Logger logger = Logger.getLogger(InstructorProgramManagement.class.getName());

    @Given("the instructor is logged into the system")
    public void the_instructor_is_logged_into_the_system() {
        logger.info("instructor signed in");

    }


}
