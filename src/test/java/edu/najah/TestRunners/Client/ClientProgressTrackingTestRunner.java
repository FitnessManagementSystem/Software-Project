package edu.najah.TestRunners.Client;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/ClientFeatures/ClientProgressTracking.feature", // Path to your feature files
        glue = "stepdefinitions/ClientStepDefinitions", // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Report generation
        monochrome = true // Makes output readable
)

public class ClientProgressTrackingTestRunner {

}
