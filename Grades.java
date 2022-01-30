/**
 * Enumerator class for the Grades;
 * 
 * @author Taimoor Qamar
 * @version 2019.09.29
 *
 */
public enum Grades {
    /**
     * Letter grade A.
     */
    A(100, "A"),
    /**
     * Letter grade A-.
     */
    AMINUS(89, "A-"),
    /**
     * Letter grade B+.
     */
    BPLUS(84, "B+"),
    /**
     * Letter grade B.
     */
    B(79, "B"),
    /**
     * Letter grade B-.
     */
    BMINUS(74, "B-"),
    /**
     * Letter grade C+.
     */
    CPLUS(69, "C+"),
    /**
     * Letter grade C.
     */
    C(64, "C"),
    /**
     * Letter grade C-.
     */
    CMINUS(59, "C-"),
    /**
     * Letter grade D+.
     */
    DPLUS(57, "D+"),
    /**
     * Letter grade D.
     */
    D(54, "D"),
    /**
     * Letter grade D-.
     */
    DMINUS(52, "D-"),
    /**
     * Letter grade F.
     */
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


    /**
     * Returns an array of strings of all the letter grades.
     * 
     * @return am array of letter grades.
     */
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
