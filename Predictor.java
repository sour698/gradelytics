// This file is inside the 'predictor' package (helps organize code in folders)
package predictor;

// This is the Predictor class — it will be used to "predict" if a student will pass
public class Predictor {

    // This is a public static method that returns a true/false value (boolean)
    // It takes two inputs: attendance (as a percentage) and average marks
    public static boolean willPass(double attendance, double avgMarks) {

        // This line checks:
        // If attendance is 75 or more AND average marks are 35 or more, return true (student will pass)
        // Otherwise, return false
        return attendance >= 75 && avgMarks >= 35;
    }
}
