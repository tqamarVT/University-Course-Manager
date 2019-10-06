/**
 * 
 */

/**
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 10/5/19
 */
public class StudentManagerTest extends student.TestCase {
    private StudentManager stud;


    /**
     * sets up the fields before every test
     */
    public void setUp() {
        stud = new StudentManager("ComplicatedStudentData.csv");
    }


    /**
     * tests the find method in StudentManager
     */
    public void testFind() {
        assertNull(stud.find("121212121"));
        assertNull(stud.find("64678619"));
        assertNull(stud.find("564678619"));
        assertEquals(stud.find("111111111"), new DetailedStudent(111111111,
            "Peter", "", "Dolan"));
    }

}
