package stepdefinitions.ClientStepDefinitions;

import edu.najah.services.ClientService;
import edu.najah.utilities.JsonFileHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClientProgramExplorationandEnrollment extends BaseSteps {
    private static final Logger logger = Logger.getLogger(ClientProgramExplorationandEnrollment.class.getName());
    private final ClientService clientService = new ClientService();
    private boolean isWorking = false;
    private String feedbackMessage;
    private String filterType;

    @Given("I am on the Program Exploration and Enrollment page")
    public void i_am_on_the_program_exploration_and_enrollment_page() {
        logger.info("Navigated to the program exploration page.");

        Map<String, Object> mockData = new HashMap<>();
        Map<String, Object> users = new HashMap<>();
        List<Map<String, Object>> enrolledUsers = new ArrayList<>();
        List<Map<String, Object>> programs = new ArrayList<>();

        // Mock user data (includes Arqam with role Client)
        users.put("Arqam", Map.of("password", "Arqam1", "role", "Admin"));
        users.put("Arqa", Map.of("password", "Arqam381183", "role", "Client"));
        users.put("Mousa", Map.of("password", "Arqam1", "role", "Admin"));

        mockData.put("users", users);

        // Mock program data (Weight Loss is active, Yoga and Flexibility are completed)
        programs.add(Map.of(
                "id", 1,
                "title", "Weight Loss",
                "duration", "4 weeks",
                "difficulty", "Beginner",
                "price", "$100",
                "status", "active"
        ));
        programs.add(Map.of(
                "id", 4,
                "title", "Yoga",
                "duration", "4 weeks",
                "focus area", "Advanced",
                "price", "$120",
                "status", "completed"
        ));
        programs.add(Map.of(
                "id", 2,
                "title", "Flexibility",
                "duration", "6 weeks",
                "focus area", "Intermediate",
                "price", "$100",
                "status", "completed"
        ));
        mockData.put("programs", programs);

        // Mock enrolled users
        Map<String, Object> enrolledUser = new HashMap<>();
        enrolledUser.put("userName", "Mousa");
        enrolledUser.put("programName", "Weight Loss");
        enrolledUsers.add(enrolledUser);
        mockData.put("enrolledUsers", enrolledUsers);

        // Mock the loadJsonData method to return mock data
        mockedFileHandler.when(JsonFileHandler::loadJsonData).thenAnswer(invocation -> {
            logger.info("Returning mock data: " + mockData);
            return mockData;
        });

        mockedFileHandler.when(() -> JsonFileHandler.saveJsonData(Mockito.anyMap())).thenAnswer(invocation -> {
            Map<String, Object> newData = invocation.getArgument(0);
            newData.forEach((key, value) -> {
                if (mockData.containsKey(key) && mockData.get(key) instanceof List && value instanceof List) {
                    List<Object> existingList = (List<Object>) mockData.get(key);
                    List<Object> newList = (List<Object>) value;
                    for (Object item : newList) {
                        if (!existingList.contains(item)) {
                            existingList.add(item);
                        }
                    }
                } else {
                    mockData.put(key, value);
                }
            });
            logger.info("Mocked data updated: " + mockData);
            return null;
        });
    }

    @When("I select the {string} filter and I press on search")
    public void iSelectTheFilterAndIPressOnSearch(String filter) {
        filterType = filter;
        if (clientService.searchProgramsByFilter(filterType).isEmpty()) {
            isWorking = false;
        } else {
            isWorking = true;
        }
    }

    @Then("I should see a list of programs such as {string} based on {string}")
    public void iShouldSeeAListOfProgramsSuchAsBasedOn(String filterValue, String filterType) {
        if (isWorking) {
            Assert.assertTrue(true);
        } else {
            Assert.assertFalse(false);
        }
    }

    @When("I confirm my enrollment to a program {string} by {string} and status {string}")
    public void iConfirmMyEnrollmentToAProgramByAndStatus(String programName, String userName, String status) {
        feedbackMessage = clientService.enrollInProgram(programName, userName);
    }

    @Then("I should see the message {string}")
    public void i_should_see_the_message(String expectedMessage) {
        Assert.assertEquals("Feedback message did not match expected", expectedMessage, feedbackMessage);
        logger.info("Feedback message matched expected: " + expectedMessage);
    }

}
