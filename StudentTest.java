/** 
 * 
 */

/**
 * Test class for the student class.
 * 
 * @author Taimoor Qamar
 * @version 2019.10.17
 *
 */
public class StudentTest extends student.TestCase {
    // FIELD
    private Student testStudent;


    /**
     * Initializes the data before each test.
     */
    public void setUp() {
        testStudent = new Student("", "Pepe", "TheFrog");
    }


    /**
     * Tests the formatPID method for all conditions.
     */
    public void testFormatPID() {
        assertEquals(testStudent.getPID(), "000000000");
        testStudent = new Student("1", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000000001");
        testStudent = new Student("11", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000000011");
        testStudent = new Student("111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000000111");
        testStudent = new Student("1111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000001111");
        testStudent = new Student("11111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000011111");
        testStudent = new Student("111111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "000111111");
        testStudent = new Student("1111111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "001111111");
        testStudent = new Student("11111111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "011111111");
        testStudent = new Student("111111111", "Pepe", "TheFrog");
        assertEquals(testStudent.getPID(), "111111111");
        testStudent = new Student(null, "Pepe", "TheFrog");
        assertNull(testStudent.getPID());

    }
}
