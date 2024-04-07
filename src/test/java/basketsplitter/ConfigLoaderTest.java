package basketsplitter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfigLoaderTest {

    @Test
    @DisplayName("Should correctly load configuration from file")
    void loadConfigSuccess() {
        String testFilePath = "path/to/correct/configuration/config.json";
        String testFileContent = "{\"testKey\": [\"testValue1\", \"testValue2\"]}";

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Path.of(testFilePath))).thenReturn(testFileContent.getBytes());

            Map<String, List<String>> result = ConfigLoader.loadConfig(testFilePath);

            assertNotNull(result);
            assertTrue(result.containsKey("testKey"));
            assertEquals(Arrays.asList("testValue1", "testValue2"), result.get("testKey"));
        }
    }

    @Test
    @DisplayName("Should throw RuntimeException when configuration file does not exist")
    void loadConfigFailure() {
        String testFilePath = "path/to/nonexistent/config.json";

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Path.of(testFilePath))).thenThrow(IOException.class);

            assertThrows(RuntimeException.class, () -> ConfigLoader.loadConfig(testFilePath));
        }
    }
}

