package stepdefinitions;
import io.cucumber.java.en.Given;

public class StepDefinitions {
    @Given("this is a basic test Scenario.")
    public void this_is_a_basic_test_scenario() {
        System.out.println("Cucumber setup is successful!");
    }
}