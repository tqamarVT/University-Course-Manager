/**
 * 
 */

/**
 * Enumerator class for the Grades;
 * 
 * @author Taimoor Qamar
 * @version 2019.09.29
 *
 */
public enum Grades {

    A(100, "A"),
    AMINUS(89, "A-"),
    BPLUS(85, "B+"),
    B(79, "B"),
    BMINUS(75, "B-"),
    CPLUS(69, "C+"),
    C(65, "C"),
    CMINUS(59, "C-"),
    DPLUS(57, "D+"),
    D(55, "D"),
    DMINUS(52, "D-"),
    F(49, "F");

    private final int score;
    private final String letterGrade;
    private int count;


    /**
     * Constructor method for the enum class.
     * 
     * @param score
     *            integer based score
     * @param letterGrade
     *            corresponding letter grade
     */
    private Grades(int score, String letterGrade) {
        this.score = score;
        this.letterGrade = letterGrade;
        this.count = 0;
    }


    /**
     * Returns the appropriate letter grade for the score passed.
     * 
     * @param score
     *            integer based score
     * @return corresponding letter grade
     */
    public static Grades getGrade(int score) {
        Grades grade = F;
        for (Grades g : values()) {
            if (score <= g.score) {
                grade = g;
            }
        }
        return grade;
    }


    /**
     * Returns a string representation of the lettergrade.
     * 
     * @return string representation of letter grade.
     */
    public String returnGrade() {
        return letterGrade;
    }


    /**
     * Updates the count of every enumerated variable.
     * 
     * @param letterGrade
     *            letterGrade corresponding to the enumeration variable to
     *            update.
     */
    public static void updateCount(String letterGrade) {
        for (Grades g : values()) {
            if (letterGrade.equalsIgnoreCase(g.letterGrade)) {
                g.count++;
            }
        }
    }


    /**
     * 
     * @return
     */
    public String returnSpecificCount(String grade) {
        String ret = null;
        for (Grades g : values()) {
            if (g.letterGrade.equalsIgnoreCase(grade)) {
                ret = String.valueOf(g.count);
            }
            else {
                continue;
            }
        }
        return ret;
    }


    /**
     * 
     * @return
     */
    public String returnTotalCount() {
        String ret = null;
        for (Grades g : values()) {
            ret = g.returnGrade() + " " + String.valueOf(g.count) + "\n";
        }
        return ret;
    }
}
