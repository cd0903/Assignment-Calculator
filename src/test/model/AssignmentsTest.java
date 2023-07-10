package model;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test class for assignment calculator
public class AssignmentsTest {
    private Assignments testAssignments;

    private Assignment testAssignment1;
    private Assignment testAssignment2;
    private Assignment testAssignment3;
    private Assignment testAssignment4;
    private Assignment testAssignment5;
    private Assignment testAssignment6;
    private Assignment testNegativeMark;

    private List<Assignment> testAddOne;
    private List<Assignment> testAddMulti;
    private List<Assignment> testAddMultiDiffANameSameCourse;
    private List<Assignment> testAddMultiSameANameDiffCourse;
    private List<Assignment> testAddDup;

    private List<Assignment> testRemoveOne;
    private List<Assignment> testRemoveMulti;
    private List<Assignment> testRemoveOneN;
    private List<Assignment> testRemoveMultiN;
    private List<Assignment> testRemoveNeg;

    @BeforeEach
    void setUp() {
        testAssignments = new Assignments("name");

        testAssignment1 = new Assignment(80,"Midterm 2","ASTR 101");
        testAssignment2 = new Assignment(92.5,"Assignment 1","BIOL 112");
        testAssignment3 = new Assignment(100,"Quiz 1","CHEM 121");
        testAssignment4 = new Assignment(78.2,"Lab 1","CHEM 123");
        testAssignment5 = new Assignment(90,"Lab 2","CHEM 123");
        testAssignment6 = new Assignment(100, "Quiz 1", "BIOL 112");
        testNegativeMark = new Assignment(-80,"N/A","CPSC 110");

        testAddOne = new ArrayList<>();
        testAddOne.add(testAssignment1);

        testAddMulti = new ArrayList<>();
        testAddMulti.add(testAssignment1);
        testAddMulti.add(testAssignment2);
        testAddMulti.add(testAssignment3);

        testAddMultiDiffANameSameCourse = new ArrayList<>();
        testAddMultiDiffANameSameCourse.add(testAssignment1);
        testAddMultiDiffANameSameCourse.add(testAssignment4);
        testAddMultiDiffANameSameCourse.add(testAssignment5);

        testAddMultiSameANameDiffCourse = new ArrayList<>();
        testAddMultiSameANameDiffCourse.add(testAssignment1);
        testAddMultiSameANameDiffCourse.add(testAssignment3);
        testAddMultiSameANameDiffCourse.add(testAssignment6);

        testAddDup = new ArrayList<>();
        testAddDup.add(testAssignment1);
        testAddDup.add(testAssignment2);

        testRemoveOne = new ArrayList<>();
        testRemoveOne.add(testAssignment2);

        testRemoveMulti = new ArrayList<>();
        testRemoveMulti.add(testAssignment3);

        testRemoveOneN = new ArrayList<>();
        testRemoveOneN.add(testAssignment1);
        testRemoveOneN.add(testAssignment2);

        testRemoveMultiN = new ArrayList<>();
        testRemoveMultiN.add(testAssignment1);
        testRemoveMultiN.add(testAssignment2);

        testRemoveNeg = new ArrayList<>();
        testRemoveNeg.add(testAssignment1);
    }

    @Test
    void testAddOneAssignment() {
        try {
            testAssignments.addAssignment(testAssignment1);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(testAddOne, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
        assertEquals(1, testAssignments.getAssignments().size());
    }

    @Test
    void testAddMultipleAssignments() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.addAssignment(testAssignment3);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(3, testAssignments.getAssignments().size());
        assertEquals(testAddMulti, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testAddMultipleAssignmentsDiffANameSameCourse() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment4);
            testAssignments.addAssignment(testAssignment5);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(3, testAssignments.getAssignments().size());
        assertEquals(testAddMultiDiffANameSameCourse, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testAddMultipleAssignmentsSameANameDiffCourse() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment3);
            testAssignments.addAssignment(testAssignment6);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(3, testAssignments.getAssignments().size());
        assertEquals(testAddMultiSameANameDiffCourse, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testAddDuplicateAssignments() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.addAssignment(testAssignment2);
            fail("Should throw DuplicateAssignmentException.");
        } catch (NegativeMarkException e) {
            fail("No exception should be thrown.");
        } catch (DuplicateAssignmentException e) {
            // expected
        }

        assertEquals(2, testAssignments.getAssignments().size());
        assertEquals(testAddDup, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testAddAssignmentNegativeMark() {
        try {
            testAssignments.addAssignment(testNegativeMark);
        } catch (NegativeMarkException e) {
            // expected
        } catch (DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }
    }

    @Test
    void testRemoveOneMatchingAssignment() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.removeAssignment(testAssignment1);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(1, testAssignments.getAssignments().size());
        assertEquals(testRemoveOne, testAssignments.getAssignments());
        assertFalse(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testRemoveMultipleMatchingAssignments() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.addAssignment(testAssignment3);
            testAssignments.removeAssignment(testAssignment1);
            testAssignments.removeAssignment(testAssignment2);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(1, testAssignments.getAssignments().size());
        assertEquals(testRemoveMulti, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment3));
    }

    @Test
    void testRemoveOneNotMatchingAssignment() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.removeAssignment(testAssignment3);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(2, testAssignments.getAssignments().size());
        assertEquals(testRemoveOneN, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
        assertFalse(testAssignments.getAssignments().contains(testAssignment3));
    }

    @Test
    void testRemoveMultipleNotMatchingAssignments() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
            testAssignments.removeAssignment(testAssignment3);
            testAssignments.removeAssignment(testAssignment4);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(2, testAssignments.getAssignments().size());
        assertEquals(testRemoveMultiN, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment2));
        assertFalse(testAssignments.getAssignments().contains(testAssignment4));
    }

    @Test
    void testRemoveAssignmentsNothingInCalculator() {
        testAssignments.removeAssignment(testAssignment3);

        assertEquals(0, testAssignments.getAssignments().size());
        assertEquals(new ArrayList<>(), testAssignments.getAssignments());
        assertFalse(testAssignments.getAssignments().contains(testAssignment1));
    }

    @Test
    void testRemoveNegativeMarkAssignment() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.removeAssignment(testNegativeMark);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(1, testAssignments.getAssignments().size());
        assertEquals(testRemoveNeg, testAssignments.getAssignments());
        assertTrue(testAssignments.getAssignments().contains(testAssignment1));
        assertFalse(testAssignments.getAssignments().contains(testNegativeMark));
    }

    @Test
    void testTotalAverageMarkOneAssignment() {
        try {
            testAssignments.addAssignment(testAssignment1);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }

        assertEquals(80, testAssignments.totalAverageMark());
    }

    @Test
    void testTotalAverageMarkMultipleAssignments() {
        try {
            testAssignments.addAssignment(testAssignment1);
            testAssignments.addAssignment(testAssignment2);
        } catch (NegativeMarkException | DuplicateAssignmentException e) {
            fail("No exception should be thrown.");
        }


        assertEquals((80+92.5)/2, testAssignments.totalAverageMark());
    }

    @Test
    void testTotalAverageMarkNothingInCalculator() {
        assertEquals(0, testAssignments.totalAverageMark());
    }

}
