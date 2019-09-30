/**
 * All possible grades (A through F including + and - except A+, F+, and F-)
 * 
 * @author Peter Dolan
 * @version 9/28/19
 *
 */
public enum Grade {
    A("A", 90, 100),
    A_MINUS("A-", 85, 89),
    B_PLUS("B+", 80, 84),
    B("B", 75, 79),
    B_MINUS("B-", 70, 74),
    C_PLUS("C+", 65, 69),
    C("C", 60, 64),
    C_MINUS("C-", 58, 59),
    D_PLUS("D+", 55, 57),
    D("D", 53, 54),
    D_MINUS("D-", 50, 52),
    F("F", 0, 49);
    private String representation;
    private int min;
    private int max;


    private Grade(String s, int small, int large) {
        representation = s;
        min = small;
        max = large;
    }


    /**
     * Gives the min score for this grade (e.g. C- = [57.50, 59.99], so in
     * integers [58, 59] and min = 58)
     * 
     * @return the integer minimum score associated with this grade
     */
    public int getMin() {
        return min;
    }


    /**
     * Gives the max score for this grade (e.g. D- = [50.00, 52.49], so in
     * integers [50, 52] and max = 52)
     * 
     * @return the integer maximum score associated with this grade
     */
    public int getMax() {
        return max;
    }


    /**
     * Gives the Grade corresponding to the given score
     * 
     * @param score
     *            the numerical representation of a grade from 0 to 100
     *            inclusive
     * @return the grade corresponding to score
     * @throws IllegalArgumentException
     *             if score < 0 || score > 101
     */
    public static Grade intToGrade(int score) {
        for (Grade g : Grade.values()) {
            if (score >= g.min && score <= g.max) {
                return g;
            }
        }
        throw new IllegalArgumentException();
    }


    /**
     * Gives the Grade corresponding to the given score
     * 
     * @param s
     *            the String representation of a grade (e.g. A-)
     * @return the grade corresponding to s
     * @throws IllegalArgumentException
     *             if s does not correspond to any grades (e.g. A+, F+, F-, Z)
     */
    public static Grade stringToGrade(String s) {
        for (Grade g : Grade.values()) {
            if (g.toString().equals(s)) {
                return g;
            }
        }
        throw new IllegalArgumentException();
    }


    /**
     * Converts the grade into a String (e.g. A_MINUS -> A-)
     */
    @Override
    public String toString() {
        return representation;
    }
}
