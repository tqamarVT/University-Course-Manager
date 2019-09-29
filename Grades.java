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
    AMINUS(85, "A-"),
    BPLUS(80, "B+"),
    B(75, "B"),
    BMINUS(70, "B-"),
    CPLUS(65, "C+"),
    C(60, "C"),
    CMINUS(57, "C-"),
    DPLUS(55, "D+"),
    D(52, "D"),
    DMINUS(50, "D-"),
    F(0, "F"),
    INVALIDP(Integer.MAX_VALUE, "INVALID SCORE"),
    INVALIDN(Integer.MIN_VALUE, "INVALID SCORE");

    private final int score;
    private final String letterGrade;


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
    }


    /**
     * Returns the appropriate letter grade for the score passed.
     * 
     * @param score
     *            integer based score
     * @return corresponding letter grade
     */
    public static Grades getGrade(int score) {
        Grades grade = INVALIDP;
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
}
