/**
 * Student record information stored in the course manager
 * 
 * @author Peter Dolan
 * @version 9/29/19
 *
 */
public class CourseStudent extends Student
    implements Comparable<CourseStudent> {
    private int sectionId;


    /**
     * Makes a new student record
     * 
     * @param secID
     *            section identification number
     * @param stuID
     *            student identification number
     * @param firstName
     *            first name
     * @param lastName
     *            last name
     * @param score
     *            the numerical representation of a student's grade [0, 100]
     */
    public CourseStudent(
        int secID,
        int stuID,
        String firstName,
        String lastName,
        int score) {
        super(String.valueOf(stuID), firstName, lastName);
        sectionId = secID;
        setScore(score);
    }


    /**
     * Makes a new student record
     * 
     * @param secID
     *            section identification number
     * @param stuID
     *            student identification number
     * @param firstName
     *            first name
     * @param lastName
     *            last name
     * @param score
     *            the numerical representation of a student's grade [0, 100]
     * @param grade
     *            A through F including +, - but not A+, F+, or F-
     */
    public CourseStudent(
        int secID,
        int stuID,
        String firstName,
        String lastName,
        int score,
        String grade) {
        super(String.valueOf(stuID), firstName, lastName);
        sectionId = secID;
        setScore(score);
        setGrade(grade);
    }


    /**
     * Makes a new student record
     * 
     * @param secID
     *            section identification number
     * @param stuID
     *            student identification number
     * @param firstName
     *            first name
     * @param lastName
     *            last name
     * @param grade
     *            A through F including +, - but not A+, F+, or F-
     */
    public CourseStudent(
        int secID,
        int stuID,
        String firstName,
        String lastName,
        String grade) {
        super(String.valueOf(stuID), firstName, lastName);
        sectionId = secID;
        setGrade(grade);
    }


    /**
     * Makes a new student record
     * 
     * @param secID
     *            section identification number
     * @param stuID
     *            student identification number
     * @param firstName
     *            first name
     * @param lastName
     *            last name
     */
    public CourseStudent(
        int secID,
        int stuID,
        String firstName,
        String lastName) {
        super(String.valueOf(stuID), firstName, lastName);
        sectionId = secID;
    }


    /**
     * Returns the student's sectionID
     * 
     * @return the student's sectionID
     */
    public int getSectionID() {
        return sectionId;
    }


    /**
     * Changes the student's sectionID
     * 
     * @param identification
     *            number used in the section
     */
    public void setSectionID(int secID) {
        sectionId = secID;
    }


    /**
     * Compares their PIDs
     * 
     * @param other
     *            the CourseStudent to which to compare
     * @return positive if this comes after other, negative if this comes before
     *         other, and 0 if they are equal.
     */
    public int compareTo(CourseStudent other) {
        return this.getPID().compareTo(other.getPID());
    }

}
