// This file is inside the 'models' package (a way to group related classes together)
package models;

// You're creating a class called "Marks" to store information about a student's marks
public class Marks {

    // This is a variable to store the student's ID (e.g., roll number)
    public int studentId;

    // This variable will store the subject name (e.g., "Math")
    public String subject;

    // This stores the marks the student got in that subject
    public int marks;

    // This is a constructor. It runs when a new "Marks" object is created
    // It takes 3 values and saves them into the variables above
    public Marks(int studentId, String subject, int marks) {
        // 'this.studentId' means the variable inside the class.
        // '=' means you're assigning the value that was passed in.
        this.studentId = studentId;

        // Store the subject name passed to the constructor
        this.subject = subject;

        // Store the marks passed to the constructor
        this.marks = marks;
    }
}
