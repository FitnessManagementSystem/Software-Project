package edu.najah.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonFileHandler {
    private JsonFileHandler() {
        throw new IllegalStateException("Utility class");
    }

    private static final String FILE_PATH = "src/main/resources/data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static Map<String, Object> loadJsonData() throws IOException {
        return objectMapper.readValue(new File(FILE_PATH), new TypeReference<Map<String, Object>>() {
        });
    }

    public static void saveJsonData(Map<String, Object> data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), data);
    }
}
