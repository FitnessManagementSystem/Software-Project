package edu.najah.TestRunners.Instructor;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/InstructorFeatures", // Path to your feature files
        glue = "stepdefinitions/InstructorStepDefinitions", // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Report generation
        monochrome = true // Makes output readable
)

public class InstructorServiceTestRunner {

}
