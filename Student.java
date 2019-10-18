import java.util.Arrays;

/**
 * // Virginia Tech Honor Code Pledge:
 * //
 * // As a Hokie, I will conduct myself with honor and integrity at all times.
 * // I will not lie, cheat, or steal, nor will I accept the actions of those
 * // who do.
 * //
 * 
 */
/**
 * This is the student class, designed to hold student information and return it
 * upon invocation.
 * 
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 2019.09.02
 *
 */
public class Student {
    // FIELDS
    private Name name;
    private int score;
    private String pid;
    private String grade;


    /**
     * Constructor method for the student class.
     * 
     * @param first
     *            The first name of the student.
     * @param last
     *            The last name of the student.
     * @param pID
     *            the pid of the student.
     */
    public Student(String pID, String first, String last) {
        this.pid = Student.formatPID(pID);
        name = new Name(first, last);
        score = 0;
        grade = "F";
    }


    /**
     * Getter method for the Name field.
     * 
     * @return the Name field.
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
     * Setter method for the score.
     * 
     * @param grade
     *            field variable grade
     */
    public void setGrade(String grade) {
        this.grade = grade;

    }


    /**
     * Getter method for the pid field variable.
     * 
     * @return the studentID field variable.
     */
    public String getPID() {
        return pid;
    }


    /**
     * Getter method for the grade field variable.
     * 
     * @return the grade field variable.
     */
    public String getGrade() {
        return grade;
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
        ret = pid + ", " + name.getFirst() + " " + name.getLast() + ", "
            + "score = " + score + "\n";
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

            else if (Integer.parseInt(this.pid) != Integer.parseInt(
                other.pid)) {
                return false;
            }

            return this.name.compareTo(other.getName()) == 0;

        }

        return false;
    }


    /**
     * Converts a PID to a nine-digit PID.
     * 
     * @param rawPID
     *            the pid to be formatted (must be less than or equal to 9
     *            characters
     * @return a 9-digit String representation of the PID with 0's at the front
     *         if necessary (or null if rawText has more than 9 characters or is
     *         null)
     */
    public static String formatPID(String rawPID) {
        if (rawPID == null) {
            return null;
        }
        int difference = 9 - rawPID.length();
        if (difference == 0) {
            return rawPID;
        }
        else {
            char[] zerosArr = new char[difference];
            Arrays.fill(zerosArr, '0');
            String zeros = new String(zerosArr);
            return zeros + rawPID;
        }
    }

}
