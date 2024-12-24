package stepdefinitions.ClientStepDefinitions;

import edu.najah.utilities.JsonFileHandler;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

public class BaseSteps {
    protected static MockedStatic<JsonFileHandler> mockedFileHandler;

    static {
        mockedFileHandler = mockStatic(JsonFileHandler.class);
    }
}
