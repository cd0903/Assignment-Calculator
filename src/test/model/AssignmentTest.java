package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// test class for assignment
class AssignmentTest {
    private Assignment testAssignment;

    @BeforeEach
    void setUp() {
        testAssignment = new Assignment(100,"Homework 1","BIOL 112");
    }

    @Test
    void testConstructor() {
        assertEquals(testAssignment.getAssignmentName(),"Homework 1");
        assertEquals(testAssignment.getCourseName(),"BIOL 112");
        assertEquals(testAssignment.getMark(),100);
    }


}