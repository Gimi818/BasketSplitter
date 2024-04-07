package basketsplitter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class ConfigLoader {

    public static Map<String, List<String>> loadConfig(String filePath) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            ObjectMapper objectMapper = new ObjectMapper();
                log.info("Successfully loaded configuration from file: {}", filePath);
            return objectMapper.readValue(jsonData, new TypeReference<Map<String, List<String>>>(){});
        } catch (IOException e) {
                log.error("Error loading configuration from file: {}", filePath, e);
            throw new RuntimeException("Failed to load configuration from file: " + filePath, e);
        }
    }
}

