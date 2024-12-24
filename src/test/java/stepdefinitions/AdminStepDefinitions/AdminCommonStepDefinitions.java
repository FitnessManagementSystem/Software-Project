package stepdefinitions.AdminStepDefinitions;

import edu.najah.services.ClientService;
import edu.najah.services.InstructorService;
import io.cucumber.java.en.Given;

public class AdminCommonStepDefinitions {
    private final ClientService clientService = new ClientService();
    private final InstructorService instructorService = new InstructorService();


}