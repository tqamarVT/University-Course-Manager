import student.TestCase;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/**
 * Tests the SaveAndLoad class using premade .csv files
 * 
 * @author Peter Dolan
 * @version 9/29/19
 *
 */
public class SaveAndLoadTest extends TestCase {

    private ByteArrayOutputStream outContent;

    private SaveAndLoad standardS;
    private SaveAndLoad basicS;
    private SaveAndLoad twoLineS;
    private SaveAndLoad complicatedS;

    private SaveAndLoad taBinaryFileS;
    private SaveAndLoad basicBinS;
    private SaveAndLoad twoLineBinS;
    private SaveAndLoad complicatedBinS;

    private SaveAndLoad taCSVcourse;
    private SaveAndLoad taCSV2course;
    private SaveAndLoad twoLineC;
    private SaveAndLoad complicatedC;


    /**
     * Assigns filenames and tests loading .csv files
     */
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        basicS = new SaveAndLoad("BasicStudentData.csv");
        twoLineS = new SaveAndLoad("TwoLineStudentData.csv");
        complicatedS = new SaveAndLoad("ComplicatedStudentData.csv");

        standardS = new SaveAndLoad("students.csv");
        taBinaryFileS = new SaveAndLoad("students.data");

        basicBinS = new SaveAndLoad("BasicStudentData.data");
        twoLineBinS = new SaveAndLoad("TwoLineStudentData.data");
        complicatedBinS = new SaveAndLoad("ComplicatedStudentData.data");

        taCSVcourse = new SaveAndLoad("CS3114.csv");
        taCSV2course = new SaveAndLoad("cs3114_2.csv");

    }


    /**
     * tests loadStudentData with premade .csv files
     */
    public void testLoadStudentData() {
        // System.out.println(new File(".").getAbsolutePath());
        // basic testing:
        DetailedStudent[] basicSArray = { new DetailedStudent(123456789,
            "Peter", "Gorman", "Dolan") };

        DetailedStudent[] testBasicS = basicS.loadStudentData();
        /**
         * for (DetailedStudent d : testBasicS) {
         * System.out.println(d.getPID() + " " + d.getName().getFirst() + " "
         * + d.getMiddleName() + " " + d.getName().getLast());
         * }
         * 
         * for (DetailedStudent d : basicSArray) {
         * System.out.println(d.getPID() + " " + d.getName().getFirst() + " "
         * + d.getMiddleName() + " " + d.getName().getLast());
         * }
         */
        assertTrue(Arrays.equals(basicS.loadStudentData(), basicSArray));
        DetailedStudent[] twoLineSArray = { new DetailedStudent(123456789,
            "Peter", "Gorman", "Dolan"), new DetailedStudent(987654321, "Peter",
                "Sweeney", "Dolan") };
        /**
         * DetailedStudent[] testTwoLine = twoLineS.loadStudentData().toArray(
         * twoLineSArray);
         * for (DetailedStudent d : testTwoLine) {
         * System.out.println(d.getPID() + " " + d.getFirstName() + " " + d
         * .getMiddleName() + " " + d.getLastName());
         * }
         */
        assertTrue(Arrays.equals(twoLineS.loadStudentData(), twoLineSArray));
        // more complicated testing:
        DetailedStudent[] complicatedSArray = { new DetailedStudent(123456789,
            "Peter", "Gorman", "Dolan"), new DetailedStudent(987654321, "Peter",
                "Sweeney", "Dolan"), new DetailedStudent(135798642, "Peter", "",
                    "Dolan"), new DetailedStudent(111111111, "Peter", "",
                        "Dolan"), new DetailedStudent(222222222,
                            "Fifteenlettersf", "Fun", "Maximumchracter") };

        /**
         * DetailedStudent[] testComplicated = complicatedS.loadStudentData();
         * System.out.println(testComplicated.getClass());
         * System.out.println(testComplicated.length);
         * for (int a = 0; a < testComplicated.length; a++) {
         * System.out.println(testComplicated[a].getPID() + " "
         * + testComplicated[a].getFirstName() + " " + testComplicated[a]
         * .getMiddleName() + " " + testComplicated[a].getLastName());
         * }
         * System.out.println(complicatedSArray.getClass());
         * System.out.println(complicatedSArray.length);
         * for (int a = 0; a < complicatedSArray.length; a++) {
         * System.out.println(complicatedSArray[a].getPID() + " "
         * + complicatedSArray[a].getFirstName() + " "
         * + complicatedSArray[a].getMiddleName() + " "
         * + complicatedSArray[a].getLastName());
         * }
         * for (int a = 0; a < testComplicated.length; a++) {
         * System.out.println(testComplicated[a].equals(complicatedSArray[a]));
         * }
         */
        assertTrue(Arrays.equals(complicatedS.loadStudentData(),
            complicatedSArray));
    }


    /**
     * calls loadStudentData. if there is no .data file, makes a new one. If
     * there is one, loads the file and then deletes it
     */
    public void testSaveAndLoadStudentData() {
        if (basicBinS.loadStudentData() == null) { // there is no previously
            // saved file
            basicBinS.saveStudentData(basicS.loadStudentData());
            twoLineBinS.saveStudentData(twoLineS.loadStudentData());
            complicatedBinS.saveStudentData(complicatedS.loadStudentData());
        }
        else { // same as for testLoadStudentData except from .data file
            DetailedStudent[] basicSArray = { new DetailedStudent(123456789,
                "Peter", "Gorman", "Dolan") };

            DetailedStudent[] twoLineSArray = { new DetailedStudent(123456789,
                "Peter", "Gorman", "Dolan"), new DetailedStudent(987654321,
                    "Peter", "Sweeney", "Dolan") };

            DetailedStudent[] complicatedSArray = { new DetailedStudent(
                123456789, "Peter", "Gorman", "Dolan"), new DetailedStudent(
                    987654321, "Peter", "Sweeney", "Dolan"),
                new DetailedStudent(135798642, "Peter", "", "Dolan"),
                new DetailedStudent(111111111, "Peter", "", "Dolan"),
                new DetailedStudent(222222222, "Fifteenlettersf", "Fun",
                    "Maximumchracter") };

            DetailedStudent[] test1 = basicBinS.loadStudentData();
            DetailedStudent[] test2 = twoLineBinS.loadStudentData();
            DetailedStudent[] test3 = complicatedBinS.loadStudentData();

            new File(basicBinS.getFilename()).delete();
            new File(twoLineBinS.getFilename()).delete();
            new File(complicatedBinS.getFilename()).delete();
            /**
             * for (int a = 0; a < test1.length; a++) {
             * System.out.println(test1[a].getPID() + " " + test1[a].getName()
             * .getFirst() + " " + test1[a].getMiddleName() + " "
             * + test1[a].getName().getLast());
             * }
             * for (int a = 0; a < basicSArray.length; a++) {
             * System.out.println(basicSArray[a].getPID() + " "
             * + basicSArray[a].getName().getFirst() + " " + basicSArray[a]
             * .getMiddleName() + " " + basicSArray[a].getName()
             * .getLast());
             * }
             * for (int a = 0; a < test1.length; a++) {
             * System.out.println(test1[a].equals(basicSArray[a]));
             * }
             * for (int a = 0; a < test2.length; a++) {
             * System.out.println(test2[a].getPID() + " " + test2[a].getName()
             * .getFirst() + " " + test2[a].getMiddleName() + " "
             * + test2[a].getName().getLast());
             * }
             * for (int a = 0; a < twoLineSArray.length; a++) {
             * System.out.println(twoLineSArray[a].getPID() + " "
             * + twoLineSArray[a].getName().getFirst() + " "
             * + twoLineSArray[a].getMiddleName() + " " + twoLineSArray[a]
             * .getName().getLast());
             * }
             * for (int a = 0; a < test2.length; a++) {
             * System.out.println(test2[a].equals(twoLineSArray[a]));
             * }
             */
            assertTrue(Arrays.equals(test1, basicSArray));
            assertTrue(Arrays.equals(test2, twoLineSArray));
            assertTrue(Arrays.equals(test3, complicatedSArray));
        }
    }


    /**
     * need to call testSaveAndLoadStudentData() twice: once to save, and once
     * to load. This loads and deletes the files.
     */
    public void testSaveAndLoadStudentDataAgain() {
        // testSaveAndLoadStudentData();
    }


    /**
     * Tests reading TA's .csv and .data files
     */
    public void testTAStudent() {
        DetailedStudent[] rawcsv = standardS.loadStudentData();
        DetailedStudent[] removeFirst = new DetailedStudent[rawcsv.length - 1];
        for (int i = 0; i < removeFirst.length; i++) {
            removeFirst[i] = rawcsv[i + 1]; // copy all but first element
                                            // (because first element is the
                                            // only difference)
        }
        assertTrue(Arrays.equals(removeFirst, taBinaryFileS.loadStudentData()));
    }


    /**
     * tests loadCourseData with TA's .csv files
     */
    public void testLoadCourseData() {
        BST<String, CourseStudent> one = new BST<>();
        one.insert("394691224", new CourseStudent(3, 394691224, "Aubrey",
            "Williamson", 100, "A"));
        one.insert("67964700", new CourseStudent(2, 67964700, "Fritz", "Hudson",
            78, "B"));
        one.insert("20380028", new CourseStudent(1, 20380028, "Sage", "Forbes",
            4, "F"));
        one.insert("256593948", new CourseStudent(2, 256593948, "Sandra",
            "Duncan", 26, "F"));
        one.inOrderTraversal();
        String test1 = outContent.toString();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        taCSV2course.loadCourseData().inOrderTraversal();
        assertEquals(outContent.toString(), test1);

        BST<String, CourseStudent> two = new BST<>();
        two.insert("394691224", new CourseStudent(1, 394691224, "Aubrey",
            "Williamson", 100, "A"));
        two.insert("67964700", new CourseStudent(2, 67964700, "Fritz", "Hudson",
            78, "B"));
        two.insert("248476061", new CourseStudent(2, 248476061, "Winter",
            "Hodge", 31, "F"));
        two.insert("291935757", new CourseStudent(2, 291935757, "Brynne",
            "Myers", 4, "F"));
        two.insert("792704751", new CourseStudent(2, 792704751, "Leroy",
            "Sherman", 65, "C+"));
        two.insert("20380028", new CourseStudent(1, 20380028, "Sage", "Forbes",
            4, "F"));
        two.insert("256593948", new CourseStudent(2, 256593948, "Sandra",
            "Duncan", 26, "F"));
        two.insert("317397180", new CourseStudent(2, 317397180, "Nigel",
            "Gonzales", 37, "F"));
        two.insert("977159896", new CourseStudent(1, 977159896, "Naomi", "Cote",
            97, "A"));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        two.inOrderTraversal();
        String test2 = outContent.toString();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        taCSVcourse.loadCourseData().inOrderTraversal();
        assertEquals(outContent.toString(), test2);
    }

}
