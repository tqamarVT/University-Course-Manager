/**
 * Student record information stored in the course manager
 * 
 * @author Peter Dolan
 * @version 9/29/19
 *
 */
public class CourseStudent extends Student {
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
        super(firstName, lastName, stuID);
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
    public CourseStudent( // still unclear if grade can be inconsistent with
                          // score (prob can)
        int secID,
        int stuID,
        String firstName,
        String lastName,
        int score,
        String grade) {
        super(firstName, lastName, stuID);
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
        super(firstName, lastName, stuID);
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
        super(firstName, lastName, stuID);
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

}
