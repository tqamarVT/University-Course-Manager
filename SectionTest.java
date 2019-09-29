import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * // Virginia Tech Honor Code Pledge:
 * //
 * // As a Hokie, I will conduct myself with honor and integrity at all times.
 * // I will not lie, cheat, or steal, nor will I accept the actions of those
 * // who do.
 * //
 * 
 */
/**
 * Test class for the section class.
 * 
 * @author Taimoor Qamar
 * @version 2019.09.28
 *
 */
public class SectionTest extends student.TestCase {
    // FIELDS
    Section testSection;


    /**
     * Sets up the initial condition before each test.
     */
    public void setUp() {
        testSection = new Section(3);
        testSection.insert("0001", "Rick", "Roll");
        testSection.insert("0002", "Charlie", "Sheen");
        testSection.insert("0003", "Tiger", "Blood");
        testSection.insert("0004", "Bryan", "Callen");
        testSection.insert("0005", "Bobby", "Lee");
        testSection.insert("0006", "Rick", "Roll");
        testSection.insert("0007", "bobby", "lee");
        testSection.insert("0008", "rick", "roll");
        testSection.insert("0009", "bryan", "callen");
        testSection.insert("0010", "Rick", "Roll");
    }


    // ----------------------------------------------------------
    /**
     * Tests the insert method for all conditions
     */
    public void testInsert() {
        // CASE 1: PID ALREADY EXISTS
        assertEquals(testSection.getIndex(), 10);
        testSection.insert("0001", "FeelsBad", "Mane");
        assertEquals(testSection.getIndex(), 10);

        // CASE 2: PID DOESNT EXIST
        testSection.insert("0011", "Kek", "Istan");
        assertEquals(testSection.getIndex(), 11);
    }


    // ----------------------------------------------------------
    /**
     * Tests the search by pid method for all conditions.
     */
    public void testSearchPID() {
        // CASE 1: PID NOT IN SECTION
        testSection.search("0000");

        // CASE 2: PID IN SECTION
        testSection.search("0003");
        assertFuzzyEquals("0003, Tiger Blood, score = 0", systemOut()
            .getHistory());
        systemOut().clearHistory();
        testSection.search("0010");
        assertFuzzyEquals("0010, Rick Roll, score = 0", systemOut()
            .getHistory());

    }


    // ----------------------------------------------------------
    /**
     * Tests the search by full name for all conditions.
     */
    public void testSearchFullName() {
        // CASE 1: NAME NOT IN SECTION
        testSection.search("Shrek", "isLove");
        assertFuzzyEquals("Number of records found: 0", systemOut()
            .getHistory());
        systemOut().clearHistory();

        // CASE 2: NAME IN SECTION ONCE
        testSection.search("Tiger", "Blood");
        assertFuzzyEquals(
            "0003, Tiger Blood, score = 0 \n Number of records found: 1",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 2: NAME IN SECTION MULTIPLE TIMES
        testSection.search("Rick", "Roll");
        assertFuzzyEquals(("0001, Rick Roll, score = 0\n"
            + "0006, Rick Roll, score = 0\n" + "0008, rick roll, score = 0\n"
            + "0010, Rick Roll, score = 0\n" + "Number of records found: 4\n"),
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    // ----------------------------------------------------------
    /**
     * Tests the score method for all conditions.
     */
    public void testScore() {

        // CASE 1: INSERT OR VALID SEARCH NOT CALLED PRIOR
        testSection.search("doesntexist");
        testSection.score(80);
        assertFuzzyEquals(
            "score command can only be called after an insert command or a successful search command with one exact output.",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 2: VALID INSERT/SEARCH BUT INVALID SCORE, < 0
        testSection.search("0001");
        systemOut().clearHistory();
        testSection.score(-1);
        assertFuzzyEquals("Scores have to be integers in range 0 to 100.",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 3: VALID INSERT/SEARCH BUT INVALID SCORE, > 100
        testSection.search("0001");
        systemOut().clearHistory();
        testSection.score(101);
        assertFuzzyEquals("Scores have to be integers in range 0 to 100.",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 4: VALID EVERYTHING
        testSection.search("0005");
        assertFuzzyEquals("0005, Bobby Lee, score = 0", systemOut()
            .getHistory());
        systemOut().clearHistory();
        // UPDATE THE SCORE
        testSection.score(80);
        // VERIFY THE UPDATE
        testSection.search("0005");
        assertFuzzyEquals("Update Bobby Lee record, score = 80\r\n"
            + "0005, Bobby Lee, score = 80", systemOut().getHistory());
        systemOut().clearHistory();

    }


    // ----------------------------------------------------------
    /**
     * Tests the remove by PID method for all conditions.
     */
    public void testRemoveByPID() {
        // CASE 1: PID ISNT IN SECTION
        testSection.remove("04430");

        // CASE 2: PID IS IN SECTION
        testSection.search("0004");
        assertFuzzyEquals("0004, Bryan Callen, score = 0\r\n", systemOut()
            .getHistory());
        systemOut().clearHistory();
        testSection.remove("0004");
        testSection.search("0004");
        testSection.search("Tiger", "blood");
        assertFuzzyEquals("0003, Tiger Blood, score = 0\r\n"
            + "Number of records found: 1\r\n", systemOut().getHistory());
        systemOut().clearHistory();
        testSection.remove("0003");
        testSection.search("Tiger", "blood");
        assertFuzzyEquals("Number of records found: 0\r\n", systemOut()
            .getHistory());
        systemOut().clearHistory();

    }


    // ----------------------------------------------------------
    /**
     * Tests the remove by PID method for all conditions.
     */
    public void testRemoveByName() {
        // CASE 1: NAME ISNT IN SECTION
        testSection.remove("The", "Rock");

        // CASE 2: NAME IS IN SECTION MULTIPLE TIMES
        testSection.search("Rick", "Roll");
        testSection.remove("Rick", "Roll");

        // CASE 3: NAME IN SECTION ONCE
        systemOut().clearHistory();
        testSection.search("tIgEr", "BlOoD");
        assertFuzzyEquals("0003, Tiger Blood, score = 0\r\n"
            + "Number of records found: 1\r\n", systemOut().getHistory());
        systemOut().clearHistory();
        testSection.remove("tIgEr", "BlOoD");
        testSection.search("tIgEr", "BlOoD");
        testSection.search("0003");
        assertFuzzyEquals("Number of records found: 0\r\n", systemOut()
            .getHistory());
        systemOut().clearHistory();

    }


    // ----------------------------------------------------------
    /**
     * Tests the dumpSection command.
     */
    public void testDumpSection() {
        testSection.dumpSection();
        // Once output format is released, come back and do a proper test.
    }


    // ----------------------------------------------------------
    /**
     * Tests the grade command.
     */
    public void testGrade() {
        testSection.search("0001");
        testSection.score(96);
        testSection.search("0006");
        testSection.score(86);
        testSection.search("0008");
        testSection.score(46);
        testSection.search("0010");
        testSection.score(20000);
        testSection.grade();
    }

}
