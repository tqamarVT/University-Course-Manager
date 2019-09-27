/**
 * 
 */

/**
 * This is the student class, designed to hold student information and return it
 * upon invocation.
 * 
 * @author Taimoor Qamar
 * @version 2019.09.02
 *
 */
public class Student {
    // FIELDS
    private String first;
    private String last;
    private int score;
    private String studentID;


    /**
     * Constructor method for the student class.
     * 
     * @param first
     *            The first name of the student.
     * @param last
     *            The last name of the student.
     */
    public Student(String first, String last) {
        this.first = first;
        this.last = last;
        this.score = 0;
    }


    /**
     * Getter method for the first name.
     * 
     * @return field variable first.
     */
    public String getFirst() {
        return this.first;
    }


    /**
     * Getter method for the last name.
     * 
     * @return field variable last.
     */
    public String getLast() {
        return this.last;
    }


    /**
     * Getter method for the score.
     * 
     * @return field variable score.
     */
    public int getScore() {
        return this.score;
    }


    /**
     * Setter method for the score.
     * 
     * @param score
     *            field variable score
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Setter method for the studentID field variable.
     * 
     * @param studentID
     *            the String to update the field variable with.
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }


    /**
     * Getter method for the studentID field variable.
     * 
     * @return the studentID field variable.
     */
    public String getStudentID() {
        return this.studentID;
    }


    /**
     * Returns a String representation of this student with all the relevant
     * student data.
     * 
     * @return String A String of the student data.
     */
    @Override
    public String toString() {
        String ret = null;
        ret = this.studentID + ", " + this.first + " " + this.last + ", "
            + "score = " + this.score;
        return ret;
    }

}
