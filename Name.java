/**
 * The Name class is used to compare names by last name and to format names.
 * 
 * @author Peter Dolan
 * @version 9/13/19
 *
 */
public class Name implements Comparable<Name> {
    private String firstName;
    private String lastName;


    /**
     * Makes a new name with the first letter capitalized and the following
     * letters lowercase.
     * 
     * @param first
     *            first name
     * @param last
     *            last name
     */
    public Name(String first, String last) { // unsure if I should check if args
                                             // first and last are null
        firstName = Name.format(first);
        lastName = Name.format(last);
    }


    /**
     * Returns a formatted version of rawText that can be used to compare
     * Strings while ignoring case.
     * 
     * @param rawText
     *            the text to be formatted
     * @return returns rawText except that the first letter is capitalized, and
     *         the rest of rawText is lower case. If rawText is null or empty,
     *         return "".
     */
    public static String format(String rawText) {
        if (rawText == null || rawText.length() < 1) {
            return "";
        }
        String lower = rawText.toLowerCase();
        String capitalizeFirstLetter = lower.substring(0, 1).toUpperCase();
        return capitalizeFirstLetter + lower.substring(1);

    }


    /**
     * Compares this name with other name. Checks last name before first name.
     * 
     * @param other
     *            the name with which we compare
     * @return this - other
     *         (i.e. negative if this is before other, 0 if same, and positive
     *         if this is after other in alphabet)
     */
    @Override
    public int compareTo(Name other) {
        if (this.lastName.equals(other.lastName)) {
            return this.firstName.compareTo(other.firstName);
        }
        return this.lastName.compareTo(other.lastName);
    }


    /**
     * Returns a string representation of name (i.e. firstName lastName)
     * 
     * @return a string representation of name (e.g. Peter Dolan)
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


    /**
     * Checks if other is a Name with the exact same first name and last name
     * 
     * @param other
     *            the Object with which this is compared
     * @return true if yes and false if no
     */
    @Override
    public boolean equals(Object other) { // because only 3 sections, lol
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (this.getClass() == other.getClass()) {
            Name s = (Name)other;
            return this.compareTo(s) == 0;
        }
        return false;
    }
}
