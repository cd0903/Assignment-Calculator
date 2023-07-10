package persistence;

import model.Assignment;
import model.Assignments;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Assignments assignments = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAssignments() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalculator.json");
        try {
            Assignments as = reader.read();
            assertEquals("Assignment", as.getName());
            assertTrue(as.getAssignments().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAssignments() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalculator.json");
        try {
            Assignments as = reader.read();
            assertEquals("Assignment", as.getName());
            List<Assignment> assignments = as.getAssignments();
            assertEquals(2, assignments.size());
            checkAssignment(95, "Midterm 1", "CPSC 110", assignments.get(0));
            checkAssignment(80, "Lab 1", "ASTR 101", assignments.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNegativeValue() {
        JsonReader reader = new JsonReader("./data/testReaderNegativeCalculator.json");
        try {
            Assignments as = reader.read();
            List<Assignment> assignments = as.getAssignments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void testReaderDuplicateValue() {
//        JsonReader reader = new JsonReader("./data/testReaderNegativeCalculator.json");
//        try {
//            Assignments as = reader.read();
//            assertEquals("Assignment", as.getName());
//            List<Assignment> assignments = as.getAssignments();
//            assertEquals(1, assignments.size());
//            checkAssignment(95, "Midterm 1", "CPSC 110", assignments.get(0));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
