package persistence;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import model.Assignment;
import model.Assignments;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Assignments assignments = new Assignments("Assignment");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCalculator() {
        try {
            Assignments assignments = new Assignments("Assignment");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalculator.json");
            writer.open();
            writer.write(assignments);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalculator.json");
            assignments = reader.read();
            assertEquals("Assignment", assignments.getName());
            assertTrue(assignments.getAssignments().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCalculator() {
        try {
            Assignments as = new Assignments("Assignment");
            as.addAssignment(new Assignment(100, "Written Assignment 1", "STAT 251"));
            as.addAssignment(new Assignment(80, "Lab 3", "CPSC 121"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalculator.json");
            writer.open();
            writer.write(as);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCalculator.json");
            as = reader.read();
            assertEquals("Assignment", as.getName());
            List<Assignment> assignments = as.getAssignments();
            assertEquals(2, assignments.size());
            checkAssignment(100, "Written Assignment 1", "STAT 251", assignments.get(0));
            checkAssignment(80, "Lab 3", "CPSC 121", assignments.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }
    }
}
