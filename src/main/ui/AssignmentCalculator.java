package ui;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import model.Assignment;
import model.Assignments;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;

public class AssignmentCalculator {

    // code was modified from TellerApp.

    private static final String JSON_DATA = "./data/assignment.json";
    private Scanner input;
    private Assignments assignments;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the calculator application
    public AssignmentCalculator() {
        runCalculator();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCalculator() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddMark();
        } else if (command.equals("r")) {
            doRemoveMark();
        } else if (command.equals("v")) {
            doViewAll();
        } else if (command.equals("oa")) {
            doCalculateOverallAverageMark();
        } else if (command.equals("s")) {
            saveAssignments();
        } else if (command.equals("l")) {
            loadAssignments();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes assignmentCalculator
    private void init() {
        assignments = new Assignments("assignment");
        input = new Scanner(System.in);
//        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_DATA);
        jsonReader = new JsonReader(JSON_DATA);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome! Please select an option from:");
        System.out.println("\ta -> Add assignment");
        System.out.println("\tr -> Remove assignment");
        System.out.println("\tv -> View all assignments");
        System.out.println("\toa -> Calculate overall average mark");
        System.out.println("\ts -> Save assignments");
        System.out.println("\tl -> Load assignments");
        System.out.println("\tq -> Quit");
    }

    /*
     * EFFECTS: Allows user to input a mark, an assignment name, and a course name.
     *          Creates an Assignment according to this information and adds it to gradesCalculator.
     *          Returns a string of information back to user
     */
    public void doAddMark() {
        Assignment assignment;

        System.out.print("Enter mark (in percentage): ");
        double mark = input.nextDouble();
        input.nextLine();

        System.out.print("Enter assignment name: ");
        String assignmentName = input.nextLine();

        System.out.print("Enter course name: ");
        String courseName = input.nextLine();

        assignment = new Assignment(mark, assignmentName, courseName);

        try {
            assignments.addAssignment(assignment);
            System.out.println("New assignment added: ");
            System.out.println("Mark: " + mark + " Assignment: " + assignmentName + " Course: " + courseName);
        } catch (NegativeMarkException e) {
            System.out.println("Cannot add assignment with negative mark...\n");
        } catch (DuplicateAssignmentException e) {
            System.out.println("Cannot add duplicate assignments...\n");
        }

    }

    /*
     * EFFECTS: Allows users to input an assignment name.
     *          Remove an assignment with name equal to user input
     */
    private void doRemoveMark() {
        String assignmentName;

        System.out.print("Enter assignment name to remove: ");
        assignmentName = input.next();

        for (Assignment assignment : assignments.getAssignments()) {
            String name = assignment.getAssignmentName();
            if (assignmentName.equals(name)) {
                assignments.removeAssignment(assignment);
                System.out.println("Assignment removed. ");
                return;
            }
        }
    }

    // EFFECTS: print all assignments onto screen
    private void doViewAll() {
        System.out.println("All Assignments: ");
        List<Assignment> loa = assignments.getAssignments();
        for (Assignment assignment : loa) {
            System.out.println(" Mark: " + assignment.getMark() + " Assignment: "
                    + assignment.getAssignmentName() + " Course: " + assignment.getCourseName());
        }
    }

    // EFFECTS: calculate the overall average mark
    private void doCalculateOverallAverageMark() {

        System.out.println("The overall average is: " + assignments.totalAverageMark());

    }

    // EFFECTS: saves the assignments to file
    private void saveAssignments() {
        try {
            jsonWriter.open();
            jsonWriter.write(assignments);
            jsonWriter.close();
            System.out.println("Saved " + assignments.getName() + " to " + JSON_DATA);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DATA);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads assignments from file
    private void loadAssignments() {
        try {
            assignments = jsonReader.read();
            System.out.println("Loaded " + assignments.getName() + " from " + JSON_DATA);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DATA);
        }
    }
}
