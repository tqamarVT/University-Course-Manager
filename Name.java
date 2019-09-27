/**
 * 
 */

/**
 * This class serves as the Key class for the BST in this project.
 * 
 * @author Taimoor Qamar
 * @version 2019.09.24
 *
 */

public class Name implements Comparable<Name> {

    // FIELDS
    private String first;
    private String last;


    /**
     * The constructor method for the Name class.
     * 
     * @param first
     *            First name
     * @param last
     *            Last name
     */
    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }


    /**
     * Getter method for the field variable first.
     * 
     * @return field variable first.
     */
    public String getFirst() {
        return this.first;
    }


    /**
     * Getter method for the field variable last.
     * 
     * @return field variable last.
     */
    public String getLast() {
        return this.last;
    }


    /**
     * compareTo method for the Name class. Compares the two field String
     * variables first and last.
     * 
     * @return an integer value, based on the result of the comparison.
     */
    @Override
    public int compareTo(Name otherName) {
        String thisFirst = this.first.toLowerCase();
        String thisLast = this.last.toLowerCase();
        String otherFirst = otherName.getFirst().toLowerCase();
        String otherLast = otherName.getLast().toLowerCase();

        if (thisLast.compareTo(otherLast) < 0) {
            return -1;
        }
        else if (thisLast.compareTo(otherLast) > 0) {
            return 1;
        }

        else if (thisLast.compareTo(otherLast) == 0) {
            if (thisFirst.compareTo(otherFirst) < 0) {

                return -1;
            }
            else if (thisFirst.compareTo(otherFirst) > 0) {
                return 1;
            }

        }

        return 0;
    }

}
