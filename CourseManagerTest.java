/**
 * 
 */

/**
 * @author Taimoor Qamar
 *
 */
public class CourseManagerTest extends student.TestCase {
    // FIELDS
    CourseManager testCourseManager;


    /**
     * Initializes all the test conditions
     */
    public void setUp() {
        testCourseManager = new CourseManager();
        testCourseManager.loadstudentdata("students_mod.csv");
        testCourseManager.loadcoursedata("CS3114_mod.csv");

    }


    /**
     * Tests loading
     */
    public void testload() {
        systemOut().clearHistory();
        CourseManager testCourseManager2 = new CourseManager();
        StudentManager.clear();
        // CASE 1: COURSE LOAD ATTEMPTED BEFORE STUDENT LOAD
        testCourseManager2.loadcoursedata("CS3114_mod.csv");
        assertFuzzyEquals(
            "Course Load Failed. You have to load Student Information file first.\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 2: STUDENT DATA LOADED SUCCESSFULLY
        testCourseManager2.loadstudentdata("students_mod.csv");
        assertFuzzyEquals("students_mod.csv successfully loaded.\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 3: COURSE DATA LOADED SUCCESSFULLY
        testCourseManager2.loadcoursedata("CS3114_mod.csv");
        assertFuzzyEquals("CS3114_mod Course has been successfully loaded.\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.section(1);
        testCourseManager.dumpsection();
        assertFuzzyEquals("Section 1 dump:\r\n" + "BST by ID:\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "977159896, Naomi Cote, score = 97\r\n" + "BST by name:\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "BST by score:\r\n" + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n" + "Size = 8",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.section(2);
        testCourseManager.dumpsection();
        assertFuzzyEquals("Section 2 dump:\r\n" + "BST by ID:\r\n"
            + "067964700, Fritz Hudson, score = 78\r\n"
            + "248476061, Winter Hodge, score = 31\r\n"
            + "256593948, Sandra Duncan, score = 26\r\n"
            + "291935757, Brynne Myers, score = 4\r\n"
            + "317397180, Nigel Gonzales, score = 37\r\n"
            + "792704751, Leroy Sherman, score = 65\r\n" + "BST by name:\r\n"
            + "256593948, Sandra Duncan, score = 26\r\n"
            + "317397180, Nigel Gonzales, score = 37\r\n"
            + "248476061, Winter Hodge, score = 31\r\n"
            + "067964700, Fritz Hudson, score = 78\r\n"
            + "291935757, Brynne Myers, score = 4\r\n"
            + "792704751, Leroy Sherman, score = 65\r\n" + "BST by score:\r\n"
            + "291935757, Brynne Myers, score = 4\r\n"
            + "256593948, Sandra Duncan, score = 26\r\n"
            + "248476061, Winter Hodge, score = 31\r\n"
            + "317397180, Nigel Gonzales, score = 37\r\n"
            + "792704751, Leroy Sherman, score = 65\r\n"
            + "067964700, Fritz Hudson, score = 78\r\n" + "Size = 6\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the insert method for all conditions.
     */
    public void testInsert() {
        systemOut().clearHistory();
        testCourseManager.section(1);
        // CASE 1: PID DOESN'T EXIST IN STUDENTMANAGER RECORDS.
        testCourseManager.insert("123456789", "Winter", "Hodge");
        assertFuzzyEquals(
            "Winter Hodge insertion failed. Wrong student information. ID doesn't exist\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: PID EXISTS BUT BELONGS TO ANOTHER STUDENT IN ANOTHER SECTION
        // (TO TEST IF IT WILL CORRECTLY PRINT "ID BELONGS TO ANOTHER STUDENT"
        // ERROR RATHER THAN THE "ALREADY REGISTERED IN A DIFFERENT SECTION"
        // ERROR.
        testCourseManager.insert("248476061", "Sandra", "Duncan");
        assertFuzzyEquals(
            "Sandra Duncan insertion failed. Wrong student information. ID belongs to another student",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 3: STUDENT ALREADY EXISTS IN THE SECTION BEING INSERTED TO.
        testCourseManager.section(1);
        testCourseManager.insert("977159896", "Naomi", "Cote");
        assertFuzzyEquals("Naomi Cote is already in section 1\r\n", systemOut()
            .getHistory());
        // CASE 4: PID EXISTS AND BELONGS TO THE SAME STUDENT BEING INSERTED,
        // BUT ALREADY REGISTERED IN A DIFFERENT SECTION.
        testCourseManager.insert("248476061", "Winter", "Hodge");
        systemOut().clearHistory();

        // CASE 5: VALID INSERT
        testCourseManager.insert("465830040", "Cain", "Buckner");
        assertFuzzyEquals("Cain Buckner inserted.\r\n", systemOut()
            .getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the searchid method for all conditions.
     */
    public void testsearchid() {
        testCourseManager.section(1);
        systemOut().clearHistory();
        // CASE 1: STUDENT WITH PID EXISTS IN ANOTHER SECTION.
        testCourseManager.searchid("256593948");
        assertFuzzyEquals(
            "Search Failed. Couldn't find any student with ID 256593948\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: STUDENT WITH PID DOESN'T EXIST AT ALL
        testCourseManager.searchid("000000000");
        assertFuzzyEquals(
            "Search Failed. Couldn't find any student with ID 000000000\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 3: STUDENT WITH PID DOES EXIST IN CURRENTSECTION (AND TESTS THE
        // SCORE FLAG)
        testCourseManager.searchid("394691224");
        assertFuzzyEquals("Found 394691224, Aubrey Williamson, score = 100\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.score(50);
        assertFuzzyEquals("Update Aubrey Williamson record, score = 50\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.score(100);
        assertFuzzyEquals(
            "score command can only be called after an insert command or a successful search command with one exact output.",
            systemOut().getHistory());
        systemOut().clearHistory();
    }


    /**
     * Tests the search by fullname method for all conditions.
     */
    public void testSearchFullName() {
        testCourseManager.section(1);
        systemOut().clearHistory();
        // CASE 1: NO RECORDS FOUND
        testCourseManager.search("Sandra", "Duncan");
        assertFuzzyEquals("search results for Sandra Duncan:\r\n"
            + "Sandra Duncan was found in 0 records in section 1", systemOut()
                .getHistory());
        systemOut().clearHistory();
        // CASE 1: 1 RECORD FOUND (AND TESTS scoreFlag)
        testCourseManager.search("Naomi", "Cote");
        systemOut().clearHistory();
        testCourseManager.score(65);
        assertFuzzyEquals("Update Naomi Cote record, score = 65\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: MULTIPLE RECORDS FOUND (AND TESTS scoreFlag)
        testCourseManager.search("Sage", "Forbes");
        assertFuzzyEquals("search results for Sage Forbes:\r\n"
            + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "Sage Forbes was found in 3 records in section 1", systemOut()
                .getHistory());
        systemOut().clearHistory();
        testCourseManager.score(65);
        assertFuzzyEquals(
            "score command can only be called after an insert command or a successful search command with one exact output. \r\n",
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the search by partial name method for all conditions.
     */
    public void testSearchPartialName() {
        testCourseManager.section(1);
        systemOut().clearHistory();
        // CASE 1: NO RECORDS FOUND
        testCourseManager.search("Taimoor");
        assertFuzzyEquals("search results for Taimoor:\r\n"
            + "Taimoor was found in 0 records in section 1", systemOut()
                .getHistory());
        systemOut().clearHistory();
        // CASE 2: 1 RECORD FOUND (AND TESTS scoreFlag)
        testCourseManager.search("Naomi");
        assertFuzzyEquals("search results for Naomi:\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "Naomi was found in 1 records in section 1\r\n", systemOut()
                .getHistory());
        systemOut().clearHistory();
        testCourseManager.score(43);
        assertFuzzyEquals("Update Naomi Cote record, score = 43\r\n",
            systemOut().getHistory());
        // CASE 3: MULTIPLE RECORDS FOUND (AND TESTS scoreFlag)
        testCourseManager.search("Jade");
        systemOut().clearHistory();
        testCourseManager.score(43);
        assertFuzzyEquals(
            "score command can only be called after an insert command or a successful search command with one exact output. \r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
    }


    /**
     * Tests the score method for all conditions.
     */
    public void testScore() {
        testCourseManager.section(1);
        systemOut().clearHistory();
        // CASE 1: CALLED AFTER VALID INSERT
        testCourseManager.insert("465830040", "Cain", "Buckner");
        testCourseManager.score(50);
        testCourseManager.score(100);
        assertFuzzyEquals("Cain Buckner inserted.\r\n"
            + "Update Cain Buckner record, score = 50\r\n"
            + "score command can only be called after an insert command or a successful search command with one exact output. \r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: CALLED AFTER VALID SEARCH BY FULL NAME
        testCourseManager.search("Naomi", "Cote");
        testCourseManager.score(50);
        assertFuzzyEquals("search results for Naomi Cote:\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "Naomi Cote was found in 1 records in section 1\r\n"
            + "Update Naomi Cote record, score = 50\r\n", systemOut()
                .getHistory());
        systemOut().clearHistory();
        // CASE 3: CALLED AFTER VALID SEARCH BY PARTIAL
        testCourseManager.search("Cote");
        testCourseManager.score(50);
        assertFuzzyEquals("search results for Cote:\r\n"
            + "977159896, Naomi Cote, score = 50\r\n"
            + "Cote was found in 1 records in section 1\r\n"
            + "Update Naomi Cote record, score = 50\r\n", systemOut()
                .getHistory());
        systemOut().clearHistory();
        // CASE 4: CALLED AFTER INVALID COMMAND
        testCourseManager.search("Cote");
        testCourseManager.loadstudentdata("students_mod.csv");
        systemOut().clearHistory();
        testCourseManager.score(50);
        assertFuzzyEquals(
            "score command can only be called after an insert command or a successful search command with one exact output. \r\n",
            systemOut().getHistory());
        systemOut().clearHistory();

        // CASE 5: CALLED WITH INVALID SCORE
        testCourseManager.search("Cote");
        testCourseManager.score(400);
        testCourseManager.search("Cote");
        testCourseManager.score(-1);
        assertFuzzyEquals("search results for Cote:\r\n"
            + "977159896, Naomi Cote, score = 50\r\n"
            + "Cote was found in 1 records in section 1\r\n"
            + "Scores have to be integers in range 0 to 100.\r\n"
            + "search results for Cote:\r\n"
            + "977159896, Naomi Cote, score = 50\r\n"
            + "Cote was found in 1 records in section 1\r\n"
            + "Scores have to be integers in range 0 to 100.\r\n", systemOut()
                .getHistory());
        systemOut().clearHistory();

        // CASE 6: VALID CALLS, TESTS THAT THE SCORE BST IS UPDATED
        // APPROPRIATELY
        testCourseManager.dumpsection();
        assertFuzzyEquals("Section 1 dump:\r\n" + "BST by ID:\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "465830040, Cain Buckner, score = 50\r\n"
            + "977159896, Naomi Cote, score = 50\r\n" + "BST by name:\r\n"
            + "465830040, Cain Buckner, score = 50\r\n"
            + "977159896, Naomi Cote, score = 50\r\n"
            + "020380028, Sage Forbes, score = 4\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "BST by score:\r\n" + "020380028, Sage Forbes, score = 4\r\n"
            + "977159896, Naomi Cote, score = 50\r\n"
            + "465830040, Cain Buckner, score = 50\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n" + "Size = 9\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.searchid("394691224");
        testCourseManager.score(0);
        testCourseManager.searchid("123456788");
        testCourseManager.score(10);
        testCourseManager.searchid("000000001");
        testCourseManager.score(20);
        testCourseManager.searchid("000000002");
        testCourseManager.score(30);
        testCourseManager.searchid("000000003");
        testCourseManager.score(40);
        testCourseManager.searchid("123456787");
        testCourseManager.score(50);
        testCourseManager.searchid("465830040");
        testCourseManager.score(60);
        testCourseManager.searchid("977159896");
        testCourseManager.score(70);
        testCourseManager.searchid("020380028");
        testCourseManager.score(80);
        systemOut().clearHistory();
        testCourseManager.dumpsection();
        assertFuzzyEquals("Section 1 dump:\r\n" + "BST by ID:\r\n"
            + "000000001, Jade Northcutt, score = 20\r\n"
            + "000000002, Brandon Jade, score = 30\r\n"
            + "000000003, Jade Jade, score = 40\r\n"
            + "020380028, Sage Forbes, score = 80\r\n"
            + "123456787, Sage Forbes, score = 50\r\n"
            + "123456788, Sage Forbes, score = 10\r\n"
            + "394691224, Aubrey Williamson, score = 0\r\n"
            + "465830040, Cain Buckner, score = 60\r\n"
            + "977159896, Naomi Cote, score = 70\r\n" + "BST by name:\r\n"
            + "465830040, Cain Buckner, score = 60\r\n"
            + "977159896, Naomi Cote, score = 70\r\n"
            + "020380028, Sage Forbes, score = 80\r\n"
            + "123456787, Sage Forbes, score = 50\r\n"
            + "123456788, Sage Forbes, score = 10\r\n"
            + "000000002, Brandon Jade, score = 30\r\n"
            + "000000003, Jade Jade, score = 40\r\n"
            + "000000001, Jade Northcutt, score = 20\r\n"
            + "394691224, Aubrey Williamson, score = 0\r\n"
            + "BST by score:\r\n"
            + "394691224, Aubrey Williamson, score = 0\r\n"
            + "123456788, Sage Forbes, score = 10\r\n"
            + "000000001, Jade Northcutt, score = 20\r\n"
            + "000000002, Brandon Jade, score = 30\r\n"
            + "000000003, Jade Jade, score = 40\r\n"
            + "123456787, Sage Forbes, score = 50\r\n"
            + "465830040, Cain Buckner, score = 60\r\n"
            + "977159896, Naomi Cote, score = 70\r\n"
            + "020380028, Sage Forbes, score = 80\r\n" + "Size = 9\r\n" + "",
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the remove by PID method for all conditions.
     */
    public void testRemovePID() {
        // CASE 1: STUDENT DOESN'T EXIST AT ALL
        testCourseManager.section(1);
        systemOut().clearHistory();
        testCourseManager.remove("840850245");
        assertFuzzyEquals(
            "Remove failed: couldn't find any student with id 840850245\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: STUDENT DOESN'T EXIST IN THAT SECTION.
        testCourseManager.remove("291935757");
        assertFuzzyEquals(
            "Remove failed: couldn't find any student with id 291935757\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 3: STUDENT EXISTS AND HAS SAME FULL NAME AS ANOTHER STUDENT IN
        // THE SECTION, SUCCESSFUL REMOVE
        testCourseManager.searchid("020380028");
        testCourseManager.remove("020380028");
        testCourseManager.searchid("020380028");
        assertFuzzyEquals("Found 020380028, Sage Forbes, score = 4\r\n"
            + "Student Sage Forbes get removed from section 1 \r\n"
            + "Search Failed. Couldn't find any student with ID 020380028",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.searchid("123456787");
        testCourseManager.searchid("123456788");
        testCourseManager.dumpsection();
        assertFuzzyEquals("Found 123456787, Sage Forbes, score = 88\r\n"
            + "Found 123456788, Sage Forbes, score = 99\r\n"
            + "Section 1 dump:\r\n" + "BST by ID:\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "977159896, Naomi Cote, score = 97\r\n" + "BST by name:\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "123456787, Sage Forbes, score = 88\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n"
            + "BST by score:\r\n" + "123456787, Sage Forbes, score = 88\r\n"
            + "000000003, Jade Jade, score = 97\r\n"
            + "977159896, Naomi Cote, score = 97\r\n"
            + "000000002, Brandon Jade, score = 98\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "123456788, Sage Forbes, score = 99\r\n"
            + "394691224, Aubrey Williamson, score = 100\r\n" + "Size = 7\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
    }


    /**
     * Tests the remove by name method for all conditions.
     */
    public void testRemoveByName() {
        systemOut().clearHistory();
        testCourseManager.section(1);
        // CASE 1: STUDENT DOESN'T EXIST
        testCourseManager.remove("Pepe", "FrogKing");
        assertFuzzyEquals(
            "Remove failed. Student Pepe FrogKing doesn't exist in section 1\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 2: STUDENT DOESN'T EXIST IN THIS SECTION
        testCourseManager.remove("Nigel", "Gonzales");
        assertFuzzyEquals(
            "Remove failed. Student Nigel Gonzales doesn't exist in section 1\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 3: MULTIPLE STUDENTS WITH THAT NAME IN THIS SECTION
        testCourseManager.remove("Sage", "Forbes");
        assertFuzzyEquals(
            "Remove failed. Student Sage Forbes doesn't exist in section 1\r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        // CASE 4: VALID REMOVE
        testCourseManager.search("Jade", "Northcutt");
        testCourseManager.remove("Jade", "Northcutt");
        testCourseManager.search("Jade", "Northcutt");
        assertFuzzyEquals("search results for Jade Northcutt:\r\n"
            + "000000001, Jade Northcutt, score = 99\r\n"
            + "Jade Northcutt was found in 1 records in section 1\r\n"
            + "Student Jade Northcutt get removed from section 1\r\n"
            + "search results for Jade Northcutt:\r\n"
            + "Jade Northcutt was found in 0 records in section 1\r\n" + "",
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the dumpSection method for all conditions.
     */
    public void testDumpSection() {
        testCourseManager.clearcoursedata();
        StudentManager.clear();
        testCourseManager.loadstudentdata("students.csv");
        testCourseManager.loadcoursedata("CS3114.csv");
        systemOut().clearHistory();
        testCourseManager.dumpsection();
        assertFuzzyEquals("section 1 dump:\r\n" + "BST by ID:\r\n"
            + "020380028, Sage Forbes, score = 4 \r\n"
            + "394691224, Aubrey Williamson, score = 100 \r\n"
            + "977159896, Naomi Cote, score = 97 \r\n" + "BST by name:\r\n"
            + "977159896, Naomi Cote, score = 97 \r\n"
            + "020380028, Sage Forbes, score = 4 \r\n"
            + "394691224, Aubrey Williamson, score = 100 \r\n"
            + "BST by score:\r\n" + "020380028, Sage Forbes, score = 4 \r\n"
            + "977159896, Naomi Cote, score = 97 \r\n"
            + "394691224, Aubrey Williamson, score = 100 \r\n" + "Size = 3",
            systemOut().getHistory());
        systemOut().clearHistory();

    }


    /**
     * Tests the grade method for all conditions.
     */
    public void testGrade() {
        systemOut().clearHistory();
        testCourseManager.section(1);
        testCourseManager.dumpsection();
        testCourseManager.searchid("394691224");
        testCourseManager.score(85);
        testCourseManager.searchid("123456788");
        testCourseManager.score(85);
        testCourseManager.searchid("000000001");
        testCourseManager.score(77);
        testCourseManager.searchid("000000002");
        testCourseManager.score(100);
        testCourseManager.searchid("000000003");
        testCourseManager.score(70);
        testCourseManager.searchid("123456787");
        testCourseManager.score(100);
        testCourseManager.searchid("977159896");
        testCourseManager.score(100);
        testCourseManager.searchid("020380028");
        testCourseManager.score(100);
        testCourseManager.grade();
        testCourseManager.stat();
        systemOut().clearHistory();
        testCourseManager.list("C*");
    }


    /**
     * Tests the findpair method for all conditions.
     */
    public void testFindPair() {
        testCourseManager.searchid("394691224");
        testCourseManager.score(100);
        testCourseManager.searchid("123456788");
        testCourseManager.score(90);
        testCourseManager.searchid("000000001");
        testCourseManager.score(80);
        testCourseManager.searchid("000000002");
        testCourseManager.score(70);
        testCourseManager.searchid("000000003");
        testCourseManager.score(60);
        testCourseManager.searchid("123456787");
        testCourseManager.score(50);
        testCourseManager.searchid("977159896");
        testCourseManager.score(40);
        testCourseManager.searchid("020380028");
        testCourseManager.score(30);
        systemOut().clearHistory();
        testCourseManager.findpair(100);
        assertFuzzyEquals(
            "Students with score difference less than or equal 100:\r\n"
                + "Jade Northcutt, Brandon Jade\r\n"
                + "Jade Northcutt, Jade Jade\r\n"
                + "Jade Northcutt, Sage Forbes\r\n"
                + "Jade Northcutt, Sage Forbes\r\n"
                + "Jade Northcutt, Sage Forbes\r\n"
                + "Jade Northcutt, Aubrey Williamson\r\n"
                + "Jade Northcutt, Naomi Cote\r\n"
                + "Brandon Jade, Jade Jade\r\n"
                + "Brandon Jade, Sage Forbes\r\n"
                + "Brandon Jade, Sage Forbes\r\n"
                + "Brandon Jade, Sage Forbes\r\n"
                + "Brandon Jade, Aubrey Williamson\r\n"
                + "Brandon Jade, Naomi Cote\r\n" + "Jade Jade, Sage Forbes\r\n"
                + "Jade Jade, Sage Forbes\r\n" + "Jade Jade, Sage Forbes\r\n"
                + "Jade Jade, Aubrey Williamson\r\n"
                + "Jade Jade, Naomi Cote\r\n" + "Sage Forbes, Sage Forbes\r\n"
                + "Sage Forbes, Sage Forbes\r\n"
                + "Sage Forbes, Aubrey Williamson\r\n"
                + "Sage Forbes, Naomi Cote\r\n" + "Sage Forbes, Sage Forbes\r\n"
                + "Sage Forbes, Aubrey Williamson\r\n"
                + "Sage Forbes, Naomi Cote\r\n"
                + "Sage Forbes, Aubrey Williamson\r\n"
                + "Sage Forbes, Naomi Cote\r\n"
                + "Aubrey Williamson, Naomi Cote\r\n" + "found 28 pairs \r\n",
            systemOut().getHistory());
        systemOut().clearHistory();
        testCourseManager.findpair(0);
        assertFuzzyEquals(
            "Students with score difference less than or equal 0:\r\n"
                + "found 0 pairs \r\n", systemOut().getHistory());
        systemOut().clearHistory();
    }


    /**
     * Tests the merge method for all conditions.
     */
    public void testMerge() {
        // CASE 1: CURRENT SECTION IS NOT CLEAR
        testCourseManager.section(1);
        testCourseManager.getSectionState();
        testCourseManager.merge();
        testCourseManager.dumpsection();
        testCourseManager.section(2);
        testCourseManager.getSectionState();
        testCourseManager.merge();
        testCourseManager.dumpsection();
        // CASE 2: CURRENT SECTION IS CLEAR (RUN ALL METHODS ON THE SECTION)
        testCourseManager.section(3);
        testCourseManager.getSectionState();
        testCourseManager.merge();
        testCourseManager.dumpsection();
        testCourseManager.insert("324324234", "firstName", "lastName");
        testCourseManager.search("000000000");
        testCourseManager.search("Tom", "Ford");
        testCourseManager.search("Tom");
        testCourseManager.score(100);
        testCourseManager.remove("324324234");
        testCourseManager.remove("Tom", "Ford");
        testCourseManager.grade();
        testCourseManager.stat(); // This test case would require calling grades
                                  // before it, handle the null.
        testCourseManager.list("A"); // This test case would require calling
                                     // grades before it, handle the null.
        testCourseManager.findpair(0);
        testCourseManager.clearsection();
        testCourseManager.getSectionState();
    }

}
