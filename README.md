# My Personal Project - Assignment Calculator

### Project Description
This application is an assignment calculator that can **compute the 
list of assignments added and the overall average.**
Users are able to add and remove assignments to and from the calculator. 
Users can also view a list of assignments currently in the calculator.

Anyone who need to calculator their grades can use this project efficiently. 
Personally, I decided to do this project because calculating grades from a course always takes
quite a long time, therefore creating this assignment calculator will really benefit me and other 
students who need a tool to calculate their grades.

### User Story

- As a user, I want to be able to add a mark for an assignment.
- As a user, I want to be able to remove a mark for an assignment.
- As a user, I want to be able to see the overall average (for all courses).
- As a user, I want to be able to see a list of assignments I have.
- As a user, I want to be able to save my assignments to file
  As a user, I want to be able to be able to load my assignments from file 

### Instructions for Grader

- You can generate the required event related to adding Xs to a Y by clicking the "Add Assignment" button.
- You can locate my visual component by locating the calculator icon on the main menu.
- You can save the state of my application by clicking the "Save Assignment" button.
- You can reload the state of my application by clicking the "Load Assignment" button.

### Phase 4: Task 2
Sun Nov 27 16:31:58 PST 2022
Assignment Added To Calculator.

Sun Nov 27 16:32:06 PST 2022
Assignment Added To Calculator.

Sun Nov 27 16:32:25 PST 2022
Removed Assignment From Calculator.

Sun Nov 27 16:32:26 PST 2022
Calculated Assignment Average.

### Phase 4: Task 3
- the UML diagram looks like this because:
  - Assignments is a list of Assignment, so it has an arity of 0...*
  - Assignments and Assignment both implement the Writable interface, so there's a dashed arrow between.
  - JsonReader and JsonWriter do not have any field associated with any other class, so there's no arrow pointing from them.
  - AssignmentCalculator has Assignments, a JsonReader and a JsonWriter, so there's 3 solid arrows.
  - AssignmentCalculatorUI has Assignments, a JsonReader and a JsonWriter, so there's 3 solid arrows as well.
  - Main does not have any field associated with any other class, so there's no arrow pointing from it.
- if more time were given to do this project, I would:
- Redesign the AssignmentCalculatorUI class, so there would be an AddAssignmentPanel class
instead of all the code about adding AddAssignmentPanel in AssignmentCalculatorUI class.
- I would also design the buttons in AssignmentCalculatorUI class in a more concise way.
- I would try to make the display more pretty (color, more image).
- The AddAssignmentPanel requires user to manually delete what they typed before entering
a new assignment, I would refactor this panel to reset every time after user has clicked the
"Return To Main Menu" button.