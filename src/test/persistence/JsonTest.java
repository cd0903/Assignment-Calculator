package persistence;

import model.Assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAssignment(double mark, String assignmentName, String courseName, Assignment a) {
        assertEquals(mark, a.getMark());
        assertEquals(assignmentName, a.getAssignmentName());
        assertEquals(courseName, a.getCourseName());
    }
}
