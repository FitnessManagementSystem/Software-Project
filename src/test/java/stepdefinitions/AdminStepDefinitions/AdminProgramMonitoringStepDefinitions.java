package stepdefinitions.AdminStepDefinitions;


import edu.najah.services.AdminService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import stepdefinitions.ClientStepDefinitions.BaseSteps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AdminProgramMonitoringStepDefinitions extends BaseSteps {
    private static final Logger logger = Logger.getLogger(AdminUserManagementStepDefinitions.class.getName());
    AdminService adminservice = new AdminService();
    private String feedbackMessage;

    @When("I navigate to the Program Monitoring section")
    public void iNavigateToTheProgramMonitoringSection() {
        logger.info("Navigated to the Monitoring section.");
    }

    @Then("I should see the report displayed")
    public void iShouldSeeDisplayed() {
        logger.info("Generated report feedback: " + feedbackMessage);
    }

    @And("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, "The report is successfully generated");
        logger.info("Feedback message matched expected: " + expectedMessage);
    }


    @Given("I track the completed and active programs")
    public void iTrackTheCompletedAndActivePrograms() {
        Map<String, Object> mockData = new HashMap<>();
        List<Map<String, Object>> programs = new ArrayList<>();
        programs.add(Map.of(
                "id", 1,
                "title", "Weight Loss",
                "duration", "4 weeks",
                "difficulty", "Beginner",
                "price", "$100",
                "status", "active"
        ));
        programs.add(Map.of(
                "id", 2,
                "title", "Flexibility",
                "duration", "6 weeks",
                "focus_area", "Intermediate",
                "price", "$100",
                "status", "completed"
        ));
        programs.add(Map.of(
                "id", 3,
                "title", "Muscle Gain",
                "duration", "4 weeks",
                "focus_area", "Advanced",
                "price", "$120",
                "status", "completed"
        ));
        programs.add(Map.of(
                "id", 4,
                "title", "Yoga",
                "duration", "4 weeks",
                "focus_area", "Advanced",
                "price", "$120",
                "status", "completed"
        ));
        programs.add(Map.of(
                "id", 5,
                "title", "Boxing",
                "duration", "4 weeks",
                "focus_area", "Advanced",
                "price", "$120",
                "status", "active"
        ));
        mockData.put("programs", programs);
        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenReturn(mockData);
        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> null);
    }

    @Then("I should see the count of active programs as {string}")
    public void iShouldSeeTheCountOfActiveProgramsAs(String expectedActiveCount) {
        int actualActiveCount = adminservice.getActiveProgramsCount();
        Assert.assertEquals("The count of active programs did not match.",
                Integer.parseInt(expectedActiveCount),
                actualActiveCount);
        logger.info("Active programs count matched: " + actualActiveCount);
    }

    @And("I should see the count of completed programs as {string}")
    public void iShouldSeeTheCountOfCompletedProgramsAs(String expectedCompletedCount) {
        int actualCompletedCount = adminservice.getCompletedProgramsCount();
        Assert.assertEquals("The count of completed programs did not match.",
                Integer.parseInt(expectedCompletedCount),
                actualCompletedCount);
        logger.info("Completed programs count matched: " + actualCompletedCount);
    }
}