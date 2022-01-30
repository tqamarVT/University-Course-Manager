/**
 * Student that contains more information such as a middle name (used for the
 * student manager)
 * 
 * @author Taimoor Qamar (tqamar)
 * @version 2019.07.15
 *
 */
public class DetailedStudent extends Student implements Comparable<Student> {
    private String middlename;


    /**
     * Makes a new DetailedStudent
     * 
     * @param firstName
     *            the student's first name
     * @param middleName
     *            the student's middle name
     * @param lastName
     *            the student's last name
     * @param id
     *            the unique identifier for the student
     */
    public DetailedStudent(
        int id,
        String firstName,
        String middleName,
        String lastName) {
        super(String.valueOf(id), firstName, lastName);
        middlename = Name.format(middleName);
    }


    /**
     * Returns the middle name of the student
     * 
     * @return middlename
     */
    public String getMiddleName() {
        return middlename;
    }


    /**
     * Changes the middle name of the student
     * 
     * @param middleName
     *            the new middle name for this student
     */
    public void setMiddleName(String middleName) {
        middlename = middleName;
    }


    /**
     * Checks whether this is the same as other. This and other are the same iff
     * they have equal PIDs, have equal-valued first names, have equal-valued
     * middle names, and have equal-valued last names.
     * 
     * @param other
     *            the object that is being compared with this for equality
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
            DetailedStudent ds = (DetailedStudent)other;
            return this.getPID().equals(ds.getPID()) && this.getName()
                .compareTo(ds.getName()) == 0 && this.getMiddleName().equals(ds
                    .getMiddleName());
        }
        return false;
    }


    /**
     * Compares this with other based on PID. If this has a larger PID, returns
     * a positive number. If this has a smaller PID, returns a negative number.
     * If the PIDs are equal, returns 0.
     * 
     * @param other
     *            the other student to compare to.
     * @return If this has a larger PID, returns
     *         a positive number. If this has a smaller PID, returns a negative
     *         number.
     *         If the PIDs are equal, returns 0. If other is null, returns 0.
     */
    public int compareTo(Student other) {
        if (other == null) {
            return 0; // cbf
        }
        return Integer.parseInt(this.getPID()) - Integer.parseInt(other
            .getPID());
    }

}
