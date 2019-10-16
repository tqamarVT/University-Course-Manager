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
    BPLUS(84, "B+"),
    B(79, "B"),
    BMINUS(74, "B-"),
    CPLUS(69, "C+"),
    C(64, "C"),
    CMINUS(59, "C-"),
    DPLUS(57, "D+"),
    D(54, "D"),
    DMINUS(52, "D-"),
    F(49, "F");

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


    public static String[] getGradeArr() {
        int i = 0;
        String[] ret = new String[12];
        for (Grades g : values()) {
            ret[i] = g.letterGrade;
            i++;
        }
        return ret;
    }
}
