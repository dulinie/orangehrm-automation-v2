package com.dulinie.automation.utils;

import com.dulinie.automation.models.SystemUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;


public class JsonDataReader {

    // 1. Reusable singleton instance to optimize memory and speed
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<SystemUser> getSystemUsers(String classpathFileName) {
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream(classpathFileName)) {
            if (input == null) {
                // Better semantic exception type for missing files
                throw new IllegalArgumentException("File not found on classpath: " + classpathFileName);
            }

            // 2. Clean, readable type-safe collection mapping using TypeReference
            return MAPPER.readValue(input, new TypeReference<List<SystemUser>>() {});

        } catch (Exception e) {
            throw new RuntimeException("Could not read test data from " + classpathFileName, e);
        }
    }
}
