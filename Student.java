/**
 * The student class holds all data on a student's record.
 * 
 * @author Peter Dolan
 * @version 9/15/19
 *
 */
public class Student {
    private final int defaultScore = 0;
    private final Grade defaultGrade = Grade.F;
    private String firstname;
    private String lastname;
    private int score;
    private int personalIdentificationNumber;
    private Grade grade;


    /**
     * Makes a new student
     * 
     * @param firstName
     *            the student's first name
     * @param lastName
     *            the stduent's last name
     * @param id
     *            the unique identifier for the student
     */
    public Student(String firstName, String lastName, int id) {
        firstname = Name.format(firstName);
        lastname = Name.format(lastName);
        personalIdentificationNumber = id;
        score = defaultScore;
        grade = defaultGrade;
    }


    /**
     * Returns the student's first name
     * 
     * @return first name
     */
    public String getFirstName() {
        return firstname;
    }


    /**
     * Returns the student's last name
     * 
     * @return last name
     */
    public String getLastName() {
        return lastname;
    }


    /**
     * Returns the student's personal identification number
     * 
     * @return pid
     */
    public int getPID() {
        return personalIdentificationNumber;
    }


    /**
     * Returns the student's score
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }


    /**
     * Returns the student's grade
     * 
     * @return grade
     */
    public Grade getGrade() {
        return grade;
    }


    /**
     * Sets the student's score
     * 
     * @param numericalScore
     *            the value to which to set the score
     */
    public void setScore(int numericalScore) {
        score = numericalScore;
    }


    /**
     * Sets the student's grade
     * 
     * @param g
     *            the value to which to set the grade
     */
    public void setGrade(String g) {
        grade = Grade.stringToGrade(g);
    }


    /**
     * Sets the student's score
     * 
     * @param g
     *            the score associated with the desired grade
     */
    public void setGrade(int g) {
        grade = Grade.intToGrade(g);
    }


    /**
     * Returns a string representation of the student's record.
     * id, firstname lastname, score = score
     * 
     * @return a string representation of the student's record
     */
    @Override
    public String toString() { // might have to adjust this
        return "0" + String.valueOf(personalIdentificationNumber) + ", "
            + firstname + " " + lastname + ", score = " + String.valueOf(score);
    }


    /**
     * Checks if other is a Student with the exact same first name, last name,
     * section id, and score.
     * 
     * @param other
     *            the Object with which this is compared
     * @return true if yes and false if no
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (this.getClass() == other.getClass()) {
            Student s = (Student)other;
            return this.toString().equals(s.toString());
        }
        return false;
    }
}
