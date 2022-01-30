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
 * Test class for the StudentIndex class.
 * 
 * @author Taimoor Qamar
 * @version 2019.09.26
 */
public class StudentIndexTest extends student.TestCase {
    // FIELDS
    private StudentIndex<Student> testStudentIndex;


    /**
     * Sets up all initial conditions before each test.
     */
    public void setUp() {
        testStudentIndex = new StudentIndex<Student>();
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor for the StudentIndex class.
     */
    public void testConstructor() {
        assertNotNull(testStudentIndex);
        assertEquals(testStudentIndex.getSize(), 0);
        assertEquals(testStudentIndex.getCapacity(), 20);
    }


    // ----------------------------------------------------------
    /**
     * Tests the add method for the StudentIndex class, also tests the private
     * expand capacity method.
     */
    public void testAdd() {
        // CASE 1: NOT AT MAX CAPACITY
        assertEquals(testStudentIndex.getSize(), 0);
        testStudentIndex.add(new Student("01000", "Rick", "Roll"));
        assertEquals(testStudentIndex.getSize(), 1);

        // CASE 2: AT MAX CAPACITY
        for (int i = 0; i < 19; i++) {
            testStudentIndex.add(new Student(String.valueOf(i), String.valueOf(
                i), String.valueOf(i)));
        }
        assertEquals(testStudentIndex.getCapacity(), 20);
        assertEquals(testStudentIndex.getSize(), 20);
        testStudentIndex.remove(0);
        assertEquals(testStudentIndex.getCapacity(), 20);
        assertEquals(testStudentIndex.getSize(), 19);
        testStudentIndex.add(new Student("01002", "NeverGonna", "GiveYouUp"));
        assertEquals(testStudentIndex.getCapacity(), 40);
        assertEquals(testStudentIndex.getSize(), 20);
    }


    // ----------------------------------------------------------
    /**
     * Tests the remove method for the StudentIndex class.
     */
    public void testRemove() {
        // CASE 1: NULL CASE
        Student temp = testStudentIndex.remove(2);
        assertNull(temp);

        // CASE 2: NON-NULL CASE
        testStudentIndex.add(new Student("01003", "Your", "Mom"));
        testStudentIndex.add(new Student("01004", "Obie", "Wan"));
        temp = new Student("01005", "Bill", "Clinton");
        testStudentIndex.add(temp);
        assertEquals(testStudentIndex.getSize(), 3);
        Student temp2 = testStudentIndex.remove(2);
        assertTrue(temp2.equals(temp));
        assertEquals(testStudentIndex.getSize(), 2);

    }


    // ----------------------------------------------------------
    /**
     * Tests the getAt method for the StudentIndex class.
     */
    public void testGetAt() {
        // CASE 1: NULL CASE
        Student temp = testStudentIndex.getAt(0);
        assertNull(temp);

        // CASE 2: NON-NULL CASE
        temp = new Student("01006", "Pizza", "Gate");
        testStudentIndex.add(new Student("01006", "Pizza", "Gate"));
        assertEquals(temp, testStudentIndex.getAt(0));
    }
}
