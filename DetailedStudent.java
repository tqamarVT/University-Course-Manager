/**
 * Student that contains more information such as a middle name (used for the
 * student manager)
 * 
 * @author Peter Dolan
 * @version 9/28/19
 *
 */
public class DetailedStudent extends Student {
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
        super(firstName, lastName, id);
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
            return this.getPID() == ds.getPID() && this.getFirstName().equals(ds
                .getFirstName()) && this.getMiddleName().equals(ds
                    .getMiddleName()) && this.getLastName().equals(ds
                        .getLastName());
        }
        return false;
    }

}
