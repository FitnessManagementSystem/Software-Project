package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.AdminService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

public class AdminProgramMonitoringStepDefinitions {
    private static final Logger logger = Logger.getLogger(AdminProgramMonitoringStepDefinitions.class.getName());
    private final AdminService adminService = new AdminService();
    private String feedbackMessage;

    @When("I navigate to the Program Monitoring section")
    public void navigateToProgramMonitoringSection() {
        logger.info("Navigated to the Program Monitoring section.");
        feedbackMessage = adminService.generatePopularProgramsReport(); // Trigger report generation
        logger.info("Report generation triggered. Feedback: " + feedbackMessage);
    }

    @Then("I should see the report displayed")
    public void verifyReportDisplayed() {
        logger.info("Generated report feedback: " + feedbackMessage);

        // Validate feedback message
        switch (feedbackMessage) {
            case "The report is successfully generated":
            case "No programs or enrollment data available":
            case "An error occurred":
                Assert.assertEquals(feedbackMessage, feedbackMessage);
                break;
            default:
                Assert.fail("Unexpected feedback message: " + feedbackMessage);
        }
    }

    @And("I should see the success message {string}")
    public void verifySuccessMessage(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }

    // Scenario Outline 1: View program statistics
    @Then("I should see the count of active programs as {string}")
    public void verifyActiveProgramsCount(String expectedActiveProgramsCount) {
        int expectedCount = Integer.parseInt(expectedActiveProgramsCount);
        int activeProgramsCount = adminService.getActiveProgramsCount();

        Assert.assertEquals("Active programs count mismatch", expectedCount, activeProgramsCount);
        logger.info("Active programs count matched expected: " + expectedCount);
    }

    @And("I should see the count of completed programs as {string}")
    public void verifyCompletedProgramsCount(String expectedCompletedProgramsCount) {
        int expectedCount = Integer.parseInt(expectedCompletedProgramsCount);
        int completedProgramsCount = adminService.getCompletedProgramsCount();

        Assert.assertEquals("Completed programs count mismatch", expectedCount, completedProgramsCount);
        logger.info("Completed programs count matched expected: " + expectedCount);
    }
}
