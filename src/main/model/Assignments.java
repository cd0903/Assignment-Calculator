package model;

import exceptions.DuplicateAssignmentException;
import exceptions.NegativeMarkException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of assignments
public class Assignments implements Writable {
    private String name;
    private List<Assignment> assignments;     // the list of assignments

    /*
     * EFFECTS: instantiates assignments as a new ArrayList and set a name
     */
    public Assignments(String name) {
        this.name = name;
        this.assignments = new ArrayList<>();
    }

    // getter
    public String getName() {
        return this.name;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    // MODIFIES: this
    // EFFECTS: adds the given assignment a to the assignment calculator (list of assignments)
    public void addAssignment(Assignment a) throws NegativeMarkException, DuplicateAssignmentException {
        if (a.getMark() < 0) {
            throw new NegativeMarkException();
        }
        if (contain(a)) {
            throw new DuplicateAssignmentException();
        }
        EventLog.getInstance().logEvent(new Event("Assignment Added To Calculator."));
        assignments.add(a);
    }

    // EFFECTS: produce true if the assignment given is already present in the list, false otherwise
    public boolean contain(Assignment a) {
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentName().equals(a.getAssignmentName())
                    && assignment.getCourseName().equals(a.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes the given assignment a from the assignment calculator (list of assignments)
    public void removeAssignment(Assignment a) {
        assignments.remove(a);
        EventLog.getInstance().logEvent(new Event("Removed Assignment From Calculator."));
    }

    // EFFECTS: calculates the overall average of all assignments
    public double totalAverageMark() {
        double totalMark = 0;
        int numAssignment = 0;
        for (Assignment a : assignments) {
            totalMark += a.getMark();
            numAssignment++;
        }
        if (totalMark > 0) {
            EventLog.getInstance().logEvent(new Event("Calculated Assignment Average."));
            return totalMark / numAssignment;
        }
        EventLog.getInstance().logEvent(new Event("Calculated Assignment Average."));
        return 0;
    }

    // EFFECTS: Outputs these Assignments as a JSONObject.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("assignments", assignmentsToJson());
        return json;
    }

    // EFFECTS: returns Assignment in this list of Assignments as a JSON array
    private JSONArray assignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment a : assignments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

}
