package persistence;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import model.Assignment;
import model.Assignments;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code modified from JsonSerializationDemo

// Represents a reader that reads assignments from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads assignments from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Assignments read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAssignments(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses assignments from JSON object and returns it
    private Assignments parseAssignments(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Assignments as = new Assignments(name);
        addAssignments(as, jsonObject);
        return as;
    }

    // MODIFIES: assignments
    // EFFECTS: parses assignment from JSON object and adds them to assignments
    private void addAssignments(Assignments assignments, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("assignments");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addAssignment(assignments, nextThingy);
        }
    }

    // MODIFIES: assignments
    // EFFECTS: parses assignment from JSON object and adds it to assignments
    private void addAssignment(Assignments as, JSONObject jsonObject) {
        Double mark = jsonObject.getDouble("mark");
        String assignmentName = jsonObject.getString("assignmentName");
        String courseName = jsonObject.getString("courseName");
        Assignment assignment = new Assignment(mark, assignmentName, courseName);
        try {
            as.addAssignment(assignment);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            e.printStackTrace();
        }
    }

}
