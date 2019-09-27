/**
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
    StudentIndex<Student> testStudentIndex;


    /**
     * Sets up all initial conditions before each test.
     */
    public void setUp() {
        testStudentIndex = new StudentIndex<Student>();
    }


    /**
     * Tests the constructor for the StudentIndex class.
     */
    public void testConstructor() {
        assertNotNull(testStudentIndex);
        assertEquals(testStudentIndex.getSize(), 0);
        assertEquals(testStudentIndex.getCapacity(), 20);
    }


    /**
     * Tests the add method for the StudentIndex class, also tests the private
     * expand capacity method.
     */
    public void testAdd() {
        // CASE 1: NOT AT MAX CAPACITY
        assertEquals(testStudentIndex.getSize(), 0);
        testStudentIndex.add(new Student("Rick", "Roll"));
        assertEquals(testStudentIndex.getSize(), 1);

        // CASE 2: AT MAX CAPACITY
        for (int i = 0; i < 19; i++) {
            testStudentIndex.add(new Student(String.valueOf(i), String.valueOf(
                i)));
        }
        assertEquals(testStudentIndex.getCapacity(), 20);
        testStudentIndex.add(new Student("NeverGonna", "GiveYouUp"));
        assertEquals(testStudentIndex.getCapacity(), 40);
    }


    /**
     * Tests the remove method for the StudentIndex class.
     */
    public void testRemove() {
        // CASE 1: NULL CASE
        Student temp = testStudentIndex.remove(2);
        assertNull(temp);

        // CASE 2: NON-NULL CASE
        testStudentIndex.add(new Student("Your", "Mom"));
        testStudentIndex.add(new Student("Obie", "Wan"));
        temp = new Student("Bill", "Clinton");
        testStudentIndex.add(temp);
        assertEquals(testStudentIndex.getSize(), 3);
        Student temp2 = testStudentIndex.remove(2);
        assertTrue(temp2.equals(temp));
        assertEquals(testStudentIndex.getSize(), 2);

    }


    /**
     * Tests the getAt method for the StudentIndex class.
     */
    public void testGetAt() {
        // CASE 1: NULL CASE
        Student temp = testStudentIndex.getAt(0);
        assertNull(temp);

        // CASE 2: NON-NULL CASE
        testStudentIndex.add(new Student("Pizza", "Gate"));
        temp = testStudentIndex.getAt(0);
        assertTrue(temp.equals(new Student("Pizza", "Gate")));
    }
}
