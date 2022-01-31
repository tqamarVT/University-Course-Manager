/**
 * 
 */

/**
 * @author Taimoor Qamar
 * @version 10/5/19
 */
public class StudentManagerTest extends student.TestCase {

    /**
     * sets up the fields before every testF
     */
    public void setUp() {
        StudentManager.load("ComplicatedStudentData.csv");
    }


    /**
     * tests the find method in StudentManager
     */
    public void testFind() {
        assertNull(StudentManager.find("121212121"));
        assertNull(StudentManager.find("64678619"));
        assertNull(StudentManager.find("564678619"));
        assertEquals(StudentManager.find("111111111"), 
            new DetailedStudent(
            111111111, "Taimoor", "", "Qamar"));
        StudentManager.clear();
    }

}
