package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an assignment having a mark, an assignment name, and a course name
public class Assignment implements Writable {
    private double mark;                // the mark of an assignment
    private String assignmentName;      // the name of an assignment
    private String courseName;          // the name of the course which the assignment belongs

    /*
     * REQUIRES: assignmentName and courseName has a non-zero length
     * EFFECTS: name on assignment is set to assignmentName; course name is set to courseName;
     */
    public Assignment(double mark, String assignmentName, String courseName) {
        this.mark = mark;
        this.assignmentName = assignmentName;
        this.courseName = courseName;
    }

    // getters
    public double getMark() {
        return mark;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    // EFFECTS: Outputs this Assignment as a JSONObject.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("assignmentName", assignmentName);
        json.put("courseName", courseName);
        json.put("mark", mark);
        return json;
    }

}
