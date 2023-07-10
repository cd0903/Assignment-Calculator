package ui;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import model.Assignment;
import model.Assignments;

import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AssignmentCalculatorUI extends JFrame implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int FIELD_WIDTH = 300;
    public static final int FIELD_HEIGHT = 50;

    public static final Color BACKGROUND = new Color(45, 45, 45);
    public static final Color BUTTON_LIGHT_GREY = new Color(179, 179, 179);
    public static final Color BUTTON_DARK_GREY = new Color(126, 126, 126);
    public static final Color BUTTON_ORANGE = new Color(255, 157, 59);

    private Assignments assignments;
    private JPanel mainPanel;
    private JPanel addAssignmentPanel;

    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton avgButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton returnToMainButton;
    private JButton addAssignmentButton;

    private JLabel gradeLabel;
    private JLabel classNameLabel;
    private JLabel asNameLabel;

    private JTextField gradeField;
    private JTextField classNameField;
    private JTextField asNameField;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_DATA = "./data/savedFile";

    // Creates a new JFrame
    public AssignmentCalculatorUI() {
        super("assignment");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND);
        add(mainPanel);
        mainPanel.setSize(new Dimension(WIDTH, HEIGHT));

        jsonWriter = new JsonWriter(JSON_DATA);
        jsonReader = new JsonReader(JSON_DATA);

        makeAddAssignmentPanel();

        mainPanel.add(Box.createRigidArea(new Dimension(WIDTH, 25)));

        addLabelnImage();

        mainPanel.add(Box.createRigidArea(new Dimension(WIDTH, 25)));

        addButtons();

        mainPanel.setVisible(true);
    }

    // EFFECTS: Adds label and image to main panel
    public void addLabelnImage() {
        JLabel label = new JLabel("Assignment Calculator");
        label.setFont(new Font("Serif", Font.PLAIN, 80));
        label.setForeground(Color.white);
        mainPanel.add(label);

        // Image from: https://commons.wikimedia.org/wiki/File:Calculator_icon.svg
        ImageIcon originalImage = new ImageIcon("./picture/Calculator_icon.png");
        ImageIcon scaledImage = new ImageIcon(originalImage.getImage()
                .getScaledInstance(originalImage.getIconWidth() / 8,
                        originalImage.getIconHeight() / 8, Image.SCALE_SMOOTH));
        JLabel newImage = new JLabel(scaledImage);
        mainPanel.add(newImage);
    }

    // EFFECTS: Add buttons to main panel
    public void addButtons() {
        addButton = new JButton("Add Assignment");
        removeButton = new JButton("Remove Assignment");
        viewButton = new JButton("View All Assignments");
        avgButton = new JButton("Calculate Overall Average Mark");
        saveButton = new JButton("Save Assignments");
        loadButton = new JButton("Load Assignments");
        quitButton = new JButton(new QuitAction());

        addButton1(addButton);
        addButton1(removeButton);
        mainPanel.add(Box.createRigidArea(new Dimension(WIDTH, 5)));
        addButton2(viewButton);
        addButton2(avgButton);
        mainPanel.add(Box.createRigidArea(new Dimension(WIDTH, 5)));
        addButton2(saveButton);
        addButton2(loadButton);
        mainPanel.add(Box.createRigidArea(new Dimension(WIDTH, 5)));
        addButton2(quitButton);
        quitButton.setBackground(BUTTON_ORANGE);
        quitButton.setForeground(Color.white);

        addButtonFunction();
    }

    // EFFECTS: Add and set font and color of buttons (version 1)
    public void addButton1(JButton button) {
        button.setFont(new Font("Serif", Font.PLAIN, 30));
        mainPanel.add(button);
        button.setBackground(BUTTON_LIGHT_GREY);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Add and set font and color of buttons (version 2)
    public void addButton2(JButton button) {
        button.setFont(new Font("Serif", Font.PLAIN, 30));
        mainPanel.add(button);
        button.setBackground(BUTTON_DARK_GREY);
        button.setForeground(Color.white);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: Adds function to buttons
    public void addButtonFunction() {

        addButton.addActionListener(this);
        addButton.setActionCommand("Add Assignment");

        removeButton.addActionListener(this);
        removeButton.setActionCommand("Remove Assignment");

        viewButton.addActionListener(this);
        viewButton.setActionCommand("View All Assignments");

        avgButton.addActionListener(this);
        avgButton.setActionCommand("Calculate Overall Average Mark");

        saveButton.addActionListener(this);
        saveButton.setActionCommand("Save Assignments");

        loadButton.addActionListener(this);
        loadButton.setActionCommand("Load Assignments");
    }

    // EFFECTS: Calls the given method when action is performed
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add Assignment")) {
            addAssignmentPanel();
        } else if (command.equals("Remove Assignment")) {
            removeAssignment();
        } else if (command.equals("View All Assignments")) {
            viewAllAssignments();
        } else if (command.equals("Calculate Overall Average Mark")) {
            calculateAverage();
        } else if (command.equals("Save Assignments")) {
            saveAssignment();
        } else if (command.equals("Load Assignments")) {
            loadAssignment();
        } else if (command.equals("Return to Main Menu")) {
            returnToMainMenu();
        } else if (command.equals("Add")) {
            addAssignmentToList();
        }
    }

    // EFFECTS: Create an Add Assignment Panel
    public void makeAddAssignmentPanel() {
        addAssignmentPanel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(addAssignmentPanel, BoxLayout.Y_AXIS);
        addAssignmentPanel.setLayout(boxlayout);
        addAssignmentPanel.setBorder(new EmptyBorder(new Insets(100, 150, 100, 150)));
        addAssignmentPanel.setBackground(Color.DARK_GRAY);
        assignments = new Assignments("name");

        addLabelsAndFields();

        addAssignmentPanel.add(returnToMainButton);

        addAssignmentPanel.add(gradeLabel);
        addAssignmentPanel.add(gradeField);
        addAssignmentPanel.add(asNameLabel);
        addAssignmentPanel.add(asNameField);
        addAssignmentPanel.add(classNameLabel);
        addAssignmentPanel.add(classNameField);

        addAssignmentPanel.add(addAssignmentButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: Adds addAssignmentPanel to JFrame
    public void addAssignmentPanel() {
        add(addAssignmentPanel);
        addAssignmentPanel.setVisible(true);
        mainPanel.setVisible(false);
    }

    // EFFECTS: Add labels and fields to addAssignmentPanel
    public void addLabelsAndFields() {
        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setForeground(Color.white);
        classNameLabel = new JLabel("Class: ");
        classNameLabel.setForeground(Color.white);
        asNameLabel = new JLabel("Assignment: ");
        asNameLabel.setForeground(Color.white);

        gradeField = new JTextField();
        classNameField = new JTextField();
        asNameField = new JTextField();

        gradeField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        classNameField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        asNameField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

        addButtonsInAsnmtPanel();
    }

    // EFFECTS: Add buttons to addAssignmentPanel
    public void addButtonsInAsnmtPanel() {
        returnToMainButton = new JButton("Return to Main Menu");
        returnToMainButton.setActionCommand("Return to Main Menu");
        returnToMainButton.addActionListener(this);
        returnToMainButton.setFont(new Font("Serif", Font.PLAIN, 50));
        returnToMainButton.setBackground(BUTTON_DARK_GREY);
        returnToMainButton.setForeground(Color.white);

        addAssignmentButton = new JButton("Add");
        addAssignmentButton.setActionCommand("Add");
        addAssignmentButton.addActionListener(this);
        addAssignmentButton.setFont(new Font("Serif", Font.PLAIN, 30));
        addAssignmentButton.setBackground(BUTTON_ORANGE);
        addAssignmentButton.setForeground(Color.white);
    }

    // EFFECTS: Function for returnToMainButton
    public void returnToMainMenu() {
        addAssignmentPanel.setVisible(false);
        mainPanel.setVisible(true);
        mainPanel.setSize(new Dimension(WIDTH, HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: Adds user input to the list of Assignment
    public void addAssignmentToList() {
        double mark;
        String assignmentName;
        String courseName;

        mark = Double.parseDouble(gradeField.getText());
        assignmentName = asNameField.getText();
        courseName = classNameField.getText();

        Assignment assignment = new Assignment(mark, assignmentName, courseName);

        try {
            assignments.addAssignment(assignment);
            JOptionPane.showMessageDialog(null, "Assignment Added! ", "Assignment Added",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (NegativeMarkException e) {
            JOptionPane.showMessageDialog(null, "Please enter a non-negative mark! ", "Error",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (DuplicateAssignmentException e) {
            JOptionPane.showMessageDialog(null,
                    "Please enter a assignment that is not already in the list! ", "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }

        returnToMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: Remove user input from the list of Assignment
    public void removeAssignment() {
        String assignmentName = JOptionPane.showInputDialog("Enter assignment name to remove: ");

        for (Assignment assignment : assignments.getAssignments()) {
            String name = assignment.getAssignmentName();
            if (assignmentName.equals(name)) {
                assignments.removeAssignment(assignment);
                JOptionPane.showMessageDialog(null, "Assignment removed! ",
                        "Assignment Removed", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
    }

    // EFFECTS: Print all assignments onto a JOptionPane
    public void viewAllAssignments() {
        List<Assignment> loa = assignments.getAssignments();
        List<String> name = new ArrayList<>();

        for (Assignment assignment : loa) {
            name.add(" Mark: " + assignment.getMark() + " Assignment: "
                    + assignment.getAssignmentName() + " Course: " + assignment.getCourseName() + "\n");
        }

        JOptionPane.showMessageDialog(null, "All Assignments: " + "\n" + name,
                "All Assignments", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: Calculates the overall average of all assignments, print result onto a JOptionPane
    public void calculateAverage() {
        String average;

        average = "Overall Average: " + assignments.totalAverageMark();

        JOptionPane.showMessageDialog(mainPanel, average, "Overall Average",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: Saves the assignments to file JSON_DATA, print file not found if didn't find the file
    public void saveAssignment() {
        try {
            jsonWriter.open();
            jsonWriter.write(assignments);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Assignments saved to file: " + JSON_DATA,
                    "Successful", JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save assignments.",
                    "File Not Found", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the assignments from JSON_DATA file if it exists, otherwise prints error
    public void loadAssignment() {
        try {
            assignments = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded assignments from: " + JSON_DATA,
                    "Successful", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_DATA,
                    "Failed", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private static class QuitAction extends AbstractAction {

        QuitAction() {
            super("Quit");
        }

        public void printLog(EventLog el) {
            for (model.Event next : el) {
                System.out.println(next.toString() + "\n\n");
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            printLog(EventLog.getInstance());
            System.exit(0);
        }
    }
}

