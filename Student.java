
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
    private Name name;
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
        name = new Name(first, last);
        this.score = 0;
    }


    /**
     * Getter method for the Name field.
     */
    public Name getName() {
        return name;
    }


    /**
     * Getter method for the score.
     * 
     * @return field variable score.
     */
    public int getScore() {
        return score;
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
        return studentID;
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
        ret = studentID + ", " + name.getFirst() + " " + name.getLast() + ", "
            + "score = " + score;
        return ret;
    }


    @Override
    public boolean equals(Object object) {

        // CASE 1
        if (object == null) {
            return false;
        }
        // CASE 2
        else if (this == object) {
            return true;
        }
        // CASE 3
        else if (this.getClass() == object.getClass()) {
            Student other = (Student)object;

            if (this.getScore() != other.getScore()) {
                return false;
            }

            else if (this.studentID != other.getStudentID()) {
                return false;
            }

            else if (this.name.compareTo(other.getName()) != 0) {
                return false;
            }
            else {
                return true;
            }

        }

        return false;
    }

}
